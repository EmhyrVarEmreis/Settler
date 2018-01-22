package pl.morecraft.dev.settler.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.graph.PrivilegeObjectNodeRepository;
import pl.morecraft.dev.settler.dao.repository.PrivilegeObjectRepository;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.graph.PrivilegeObjectNode;
import pl.morecraft.dev.settler.domain.jtb.JTBObjectPrivilege;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectPrivilege.jTBObjectPrivilege;

@Service
public class GraphService {

    public static final long ETERNAL_PRV_NODE_ID = -1;

    private static Logger log = LoggerFactory.getLogger(GraphService.class);

    @PersistenceContext
    private final EntityManager entityManager;

    private PrivilegeObjectNodeRepository privilegeObjectNodeRepository;
    private PrivilegeObjectRepository privilegeObjectRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public GraphService(
            EntityManager entityManager,
            PrivilegeObjectNodeRepository privilegeObjectNodeRepository,
            PrivilegeObjectRepository privilegeObjectRepository,
            SessionFactory sessionFactory
    ) {
        this.entityManager = entityManager;
        this.privilegeObjectNodeRepository = privilegeObjectNodeRepository;
        this.privilegeObjectRepository = privilegeObjectRepository;
        this.sessionFactory = sessionFactory;
    }

    public void doThings() {
        log.debug("GraphService.doThings");

//        PrivilegeObjectNode privilegeObjectNode = PrivilegeObjectNode.from(ETERNAL_PRV_NODE_ID);
//        System.out.println(privilegeObjectNodeRepository.save(privilegeObjectNode).getId());
//        System.out.println(privilegeObjectNodeRepository.findOneByPrivilegeObjectId(ETERNAL_PRV_NODE_ID));
//        System.out.println(privilegeObjectNodeRepository.findOne(ETERNAL_PRV_NODE_ID));

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger auxiliaryCounter = new AtomicInteger();

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
        log.debug("Loaded {} entities", privilegeObjectMap.size());

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

        counter.set(0);
        final List<JTBObjectPrivilege> jtbObjectPrivileges = new JPAQuery<JTBObjectPrivilege>(entityManager).from(jTBObjectPrivilege).fetch();
        jtbObjectPrivileges.forEach(objectPrivilege -> {
            log.debug("Processing privileges {} of {}", counter.incrementAndGet(), jtbObjectPrivileges.size());
            if (Objects.isNull(objectPrivilege)) {
                log.warn("JTBObjectPrivilege IS NULL");
                return;
            }
            final PrivilegeObjectNode privilegeObjectNodeFrom = privilegeObjectMap.get(objectPrivilege.getObjectFrom().getId());
            final PrivilegeObjectNode privilegeObjectNodeTo = privilegeObjectMap.get(Objects.isNull(objectPrivilege.getObjectTo()) ? ETERNAL_PRV_NODE_ID : objectPrivilege.getObjectTo().getId());
            final OperationType operationType = objectPrivilege.getOperationType();
            if (operationType == OperationType.ADM) {
                privilegeObjectNodeFrom.getCanAdministrate().add(privilegeObjectNodeTo);
            } else if (operationType == OperationType.EDT) {
                privilegeObjectNodeFrom.getCanEdit().add(privilegeObjectNodeTo);
            } else if (operationType == OperationType.RDM) {
                privilegeObjectNodeFrom.getCanRead().add(privilegeObjectNodeTo);
            } else if (operationType == OperationType.CRT) {
                privilegeObjectNodeFrom.getCanCreate().add(privilegeObjectNodeTo);
            }
        });
        log.debug("Privileges processed");

        counter.set(0);
        final Collection<PrivilegeObjectNode> privilegeObjectNodesModified = privilegeObjectMap.values();
        privilegeObjectNodesModified.forEach(privilegeObjectNode -> {
            log.debug("Saving {} of {}", counter.incrementAndGet(), privilegeObjectNodesModified.size());
            privilegeObjectNodeRepository.save(privilegeObjectNode);
        });
        log.debug("PrivilegeObjectNodes saved");

        log.debug("Verification");
        final Session session = sessionFactory.openSession();
        counter.set(0);
        auxiliaryCounter.set(0);
        final HashMap<String, Object> parameters = new HashMap<>();
        jtbObjectPrivileges.forEach(objectPrivilege -> {
            if (Objects.isNull(objectPrivilege)) {
                log.warn("JTBObjectPrivilege IS NULL");
                return;
            }
            log.debug("Verifying privilege {} of {}", counter.incrementAndGet(), jtbObjectPrivileges.size());
            parameters.put("aId", objectPrivilege.getObjectFrom().getId());
            parameters.put("bId", Objects.isNull(objectPrivilege.getObjectTo()) ? ETERNAL_PRV_NODE_ID : objectPrivilege.getObjectTo().getId());
            parameters.put("relation", objectPrivilege.getOperationType().getCode());
            final Object result = session.query(
                    "RETURN EXISTS( (:PrivilegeObjectNode {privilegeObjectId: {aId}})-[:{relation}]- (:PrivilegeObjectNode {privilegeObjectId: {bId}})) AS result",
                    parameters
            ).iterator().next().get("result");
            if (!(result instanceof Boolean && (boolean) result)) {
                log.warn("Bad privilege {}-[{}]-{}", parameters.get("aId"), parameters.get("relation"), parameters.get("bId"));
                auxiliaryCounter.incrementAndGet();
            }
        });
        log.debug("Privileges verified; Invalid privileges: {}", auxiliaryCounter.get());
    }

}
