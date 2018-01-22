package pl.morecraft.dev.settler.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import java.util.HashMap;
import java.util.Objects;

@Service
public class GraphService {

    @SuppressWarnings("WeakerAccess")
    public static final long ETERNAL_PRV_NODE_ID = -1;

    private Session session;

    @Autowired
    public GraphService(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Session session
    ) {
        this.session = session;
    }

    public boolean isAuthorized(Long sourceId, Long targetId, OperationType operationType) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("aId", sourceId);
        parameters.put("bId", Objects.isNull(targetId) ? ETERNAL_PRV_NODE_ID : targetId);
        parameters.put("relation", operationType.getCode());
        final Object result = session.query(
                "RETURN EXISTS( (:PrivilegeObjectNode {privilegeObjectId: {aId}})-[:{relation}]- (:PrivilegeObjectNode {privilegeObjectId: {bId}})) AS result",
                parameters
        ).iterator().next().get("result");
        return result instanceof Boolean && (boolean) result;
    }

}
