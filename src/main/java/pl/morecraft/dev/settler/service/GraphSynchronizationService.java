package pl.morecraft.dev.settler.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.graph.PrivilegeObjectNodeRepository;
import pl.morecraft.dev.settler.dao.repository.*;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.QPrivilege;
import pl.morecraft.dev.settler.domain.QRoleAssignment;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.graph.PrivilegeObjectNode;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static pl.morecraft.dev.settler.service.GraphService.ETERNAL_PRV_NODE_ID;

@Service
public class GraphSynchronizationService {

    private static Logger log = LoggerFactory.getLogger(GraphSynchronizationService.class);

    @PersistenceContext
    private final EntityManager entityManager;

    private final Session session;
    private final GraphService graphService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SequenceManager sequenceManager;
    private final TransactionService transactionService;
    private final CategoryRepository categoryRepository;
    private final PrivilegeRepository privilegeRepository;
    private final TransactionRepository transactionRepository;
    private final RoleAssignmentRepository roleAssignmentRepository;
    private final PrivilegeObjectRepository privilegeObjectRepository;
    private final PrivilegeObjectNodeRepository privilegeObjectNodeRepository;

    @Autowired
    public GraphSynchronizationService(
            EntityManager entityManager,
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Session session,
            GraphService graphService,
            RoleRepository roleRepository,
            UserRepository userRepository,
            SequenceManager sequenceManager,
            TransactionService transactionService,
            CategoryRepository categoryRepository,
            PrivilegeRepository privilegeRepository,
            TransactionRepository transactionRepository,
            RoleAssignmentRepository roleAssignmentRepository,
            PrivilegeObjectRepository privilegeObjectRepository,
            PrivilegeObjectNodeRepository privilegeObjectNodeRepository
    ) {
        this.entityManager = entityManager;
        this.session = session;
        this.graphService = graphService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.sequenceManager = sequenceManager;
        this.transactionService = transactionService;
        this.categoryRepository = categoryRepository;
        this.privilegeRepository = privilegeRepository;
        this.transactionRepository = transactionRepository;
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.privilegeObjectRepository = privilegeObjectRepository;
        this.privilegeObjectNodeRepository = privilegeObjectNodeRepository;
    }

    public void doThings() {
        log.debug("GraphService.doThings");

        AtomicInteger counter = new AtomicInteger();

        log.debug("Loading...");
        final Map<Long, PrivilegeObjectNode> privilegeObjectMap = privilegeObjectRepository.findAll().parallelStream().map(
                privilegeObject -> PrivilegeObjectNode.from(privilegeObject.getId())
        ).peek(
                privilegeObjectNode -> {
                    privilegeObjectNode.setCanAdministrate(new HashSet<>());
                    privilegeObjectNode.setCanEdit(new HashSet<>());
                    privilegeObjectNode.setCanRead(new HashSet<>());
                    privilegeObjectNode.setCanCreate(new HashSet<>());
                }
        ).collect(Collectors.toMap(
                PrivilegeObjectNode::getPrivilegeObjectId,
                prvObject -> prvObject
        ));
        log.debug("Loaded {} PrivilegeObjects", privilegeObjectMap.size());

        log.debug("Adding eternal PrivilegeObjectNode");
        final PrivilegeObjectNode eternalPrivilegeObjectNode = PrivilegeObjectNode.from(ETERNAL_PRV_NODE_ID);
        eternalPrivilegeObjectNode.setCanAdministrate(new HashSet<>());
        eternalPrivilegeObjectNode.setCanEdit(new HashSet<>());
        eternalPrivilegeObjectNode.setCanRead(new HashSet<>());
        eternalPrivilegeObjectNode.setCanCreate(new HashSet<>());
        privilegeObjectMap.put(ETERNAL_PRV_NODE_ID, eternalPrivilegeObjectNode);

        counter.set(0);
        final Collection<PrivilegeObjectNode> privilegeObjectNodes = privilegeObjectMap.values();
        privilegeObjectNodes.forEach(privilegeObjectNode -> {
            log.debug("Saving {} of {}", counter.incrementAndGet(), privilegeObjectNodes.size());
            privilegeObjectNodeRepository.save(privilegeObjectNode);
        });
        log.debug("PrivilegeObjectNodes saved");

        log.debug("Processing Privileges...");
        privilegeRepository.findAll().forEach(privilege -> {
            if (Objects.nonNull(privilege.getPrvObject())) {
                addToNode(privilegeObjectMap, privilege.getPrvOwner(), privilege.getPrvObject(), privilege.getOperationType());
            }
        });
        log.debug("Privileges processed");

        log.debug("Processing RoleAssignments...");
        counter.set(0);
        //noinspection CodeBlock2Expr
        new JPAQuery<Tuple>(entityManager).from(QRoleAssignment.roleAssignment).select(
                QRoleAssignment.roleAssignment.user.id,
                QRoleAssignment.roleAssignment.role.id,
                QRoleAssignment.roleAssignment.target.id
        ).fetch().forEach(roleAssignmentTuple -> { // Handle RoleAssignments with target=null
            if (Objects.isNull(roleAssignmentTuple)) {
                log.error("RoleAssignment is null");
                return;
            }
            //noinspection CodeBlock2Expr
            privilegeRepository.findAll(QPrivilege.privilege.prvOwner.id.eq(Objects.requireNonNull(roleAssignmentTuple.get(1, Long.class)))).forEach(privilege -> {
                addToNode(
                        privilegeObjectMap,
                        PrivilegeObject.from(Objects.requireNonNull(roleAssignmentTuple.get(0, Long.class))),
                        Objects.isNull(roleAssignmentTuple.get(2, Long.class)) ? null : PrivilegeObject.from(roleAssignmentTuple.get(2, Long.class)),
                        privilege.getOperationType()
                );
            });
        });
        log.debug("RoleAssignments processed");

        counter.set(0);
        final Collection<PrivilegeObjectNode> privilegeObjectNodesModified = privilegeObjectMap.values();
        privilegeObjectNodesModified.forEach(privilegeObjectNode -> {
            log.debug("Saving {} of {}", counter.incrementAndGet(), privilegeObjectNodesModified.size());
            privilegeObjectNodeRepository.save(privilegeObjectNode);
        });
        log.debug("PrivilegeObjectNodes saved");
    }

    private static void addToNode(Map<Long, PrivilegeObjectNode> privilegeObjectMap, PrivilegeObject from, PrivilegeObject to, OperationType operationType) {
        final PrivilegeObjectNode privilegeObjectNodeFrom = privilegeObjectMap.get(from.getId());
        final PrivilegeObjectNode privilegeObjectNodeTo = privilegeObjectMap.get(Objects.isNull(to) ? ETERNAL_PRV_NODE_ID : to.getId());
        log.debug("Adding {}-[{}]-{}", privilegeObjectNodeFrom.getPrivilegeObjectId(), operationType.getCode(), privilegeObjectNodeTo.getId());
        if (operationType == OperationType.ADM) {
            privilegeObjectNodeFrom.getCanAdministrate().add(privilegeObjectNodeTo);
        } else if (operationType == OperationType.EDT) {
            privilegeObjectNodeFrom.getCanEdit().add(privilegeObjectNodeTo);
        } else if (operationType == OperationType.RDM) {
            privilegeObjectNodeFrom.getCanRead().add(privilegeObjectNodeTo);
        } else if (operationType == OperationType.CRT) {
            privilegeObjectNodeFrom.getCanCreate().add(privilegeObjectNodeTo);
        }
    }

}
