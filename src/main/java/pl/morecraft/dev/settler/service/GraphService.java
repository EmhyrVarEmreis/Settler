package pl.morecraft.dev.settler.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GraphService {

    @SuppressWarnings("WeakerAccess")
    public static final long ETERNAL_PRV_NODE_ID = -1;
    @SuppressWarnings("WeakerAccess")
    public static final long MAX_DEPTH = 5;

    private Session session;

    @Autowired
    public GraphService(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Session session
    ) {
        this.session = session;
    }

    public boolean isAuthorized(Long sourceId, Long targetId, OperationType operationType) {
        final Object result = session.query("" +
                        "RETURN EXISTS(" +
                        " (:PrivilegeObjectNode {privilegeObjectId: {fromId}})-[:{relation}]->(:PrivilegeObjectNode {privilegeObjectId: {toId}})" +
                        ") OR EXISTS(" +
                        " (:PrivilegeObjectNode {privilegeObjectId: {fromId}})-[:{relation}]->(:PrivilegeObjectNode)-[*..{maxDepth}]->(:PrivilegeObjectNode {privilegeObjectId: {toId}})" +
                        ") AS result",
                prepareHashMap(sourceId, targetId, operationType)
        ).iterator().next().get("result");
        return result instanceof Boolean && (boolean) result;
    }

    public void processTransaction(Transaction oldTransaction, Transaction newTransaction) {
        boolean isOldNull = Objects.isNull(oldTransaction);
        if (isOldNull) {
            addRelation(newTransaction.getCreator(), newTransaction, OperationType.ADM);
            for (Redistribution redistribution : newTransaction.getOwners()) {
                addRelation(redistribution.getId().getUser(), newTransaction, OperationType.RDM);
            }
        } else {
            if (oldTransaction.getCreator().getId().longValue() != newTransaction.getCreator().getId().longValue()) {
                removeRelation(oldTransaction.getCreator(), newTransaction, OperationType.ADM);
                addRelation(newTransaction.getCreator(), newTransaction, OperationType.ADM);
            }
            if (areRedistributionsDifferent(newTransaction.getOwners(), oldTransaction.getOwners())) {
                for (Redistribution redistribution : oldTransaction.getOwners()) {
                    removeRelation(redistribution.getId().getUser(), newTransaction, OperationType.RDM);
                }
                for (Redistribution redistribution : newTransaction.getOwners()) {
                    addRelation(redistribution.getId().getUser(), newTransaction, OperationType.RDM);
                }
            }
            if (areRedistributionsDifferent(newTransaction.getContractors(), oldTransaction.getContractors())) {
                for (Redistribution redistribution : oldTransaction.getContractors()) {
                    removeRelation(redistribution.getId().getUser(), newTransaction, OperationType.RDM);
                }
                for (Redistribution redistribution : newTransaction.getContractors()) {
                    addRelation(redistribution.getId().getUser(), newTransaction, OperationType.RDM);
                }
            }
        }
    }

    private boolean areRedistributionsDifferent(List<Redistribution> a, List<Redistribution> b) {
        final List<Long> aa = a.stream().map(redistribution -> redistribution.getId().getUser().getId()).collect(Collectors.toList());
        final List<Long> bb = b.stream().map(redistribution -> redistribution.getId().getUser().getId()).collect(Collectors.toList());
        return aa.size() != bb.size() || !aa.containsAll(bb);
    }

    private void removeRelation(PrivilegeObject privilegeObjectFrom, PrivilegeObject privilegeObjectTo, OperationType operationType) {
        session.query(
                "MATCH (:PrivilegeObjectNode {privilegeObjectId: {fromId}})-[r:{relation}]-(:PrivilegeObjectNode {privilegeObjectId: {toId}}) DELETE r",
                prepareHashMap(privilegeObjectFrom.getId(), Objects.isNull(privilegeObjectTo) ? null : privilegeObjectTo.getId(), operationType)
        );
    }

    private void addRelation(PrivilegeObject privilegeObjectFrom, PrivilegeObject privilegeObjectTo, OperationType operationType) {
        session.query(
                "MATCH (a:PrivilegeObjectNode {privilegeObjectId: {fromId}}), (b:PrivilegeObjectNode {privilegeObjectId: {toId}}) CREATE (a)-[:{relation}]->(b)",
                prepareHashMap(privilegeObjectFrom.getId(), Objects.isNull(privilegeObjectTo) ? null : privilegeObjectTo.getId(), operationType)
        );
    }

    private HashMap<String, Object> prepareHashMap(Long sourceId, Long targetId, OperationType operationType) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("maxDepth", MAX_DEPTH);
        parameters.put("fromId", sourceId);
        parameters.put("toId", Objects.isNull(targetId) ? ETERNAL_PRV_NODE_ID : targetId);
        parameters.put("relation", operationType.getCode());
        return parameters;
    }


}
