package pl.morecraft.dev.settler.dao.graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.morecraft.dev.settler.domain.graph.PrivilegeObjectNode;

public interface PrivilegeObjectNodeRepository extends Neo4jRepository<PrivilegeObjectNode, Long> {

    PrivilegeObjectNode findOneByPrivilegeObjectId(Long privilegeObjectId);

}
