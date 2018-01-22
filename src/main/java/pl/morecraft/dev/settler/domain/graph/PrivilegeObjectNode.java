package pl.morecraft.dev.settler.domain.graph;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;
import pl.morecraft.dev.settler.domain.PrivilegeObject;

import java.util.Set;

@NodeEntity
@Getter
@Setter
public class PrivilegeObjectNode {

    @Getter
    @Setter
    protected Long id;

    @Id
    @Getter
    @Setter
    @Property(name = "privilegeObjectId")
    protected Long privilegeObjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrivilegeObjectNode)) {
            return false;
        }

        PrivilegeObjectNode that = (PrivilegeObjectNode) o;

        return !(privilegeObjectId == null || that.privilegeObjectId == null) && privilegeObjectId.equals(that.privilegeObjectId);
    }

    @Override
    public int hashCode() {
        return privilegeObjectId.hashCode();
    }

    @Relationship(type = "ADM")
    Set<PrivilegeObjectNode> canAdministrate;

    @Relationship(type = "EDT")
    Set<PrivilegeObjectNode> canEdit;

    @Relationship(type = "RDM")
    Set<PrivilegeObjectNode> canRead;

    @Relationship(type = "CRT")
    Set<PrivilegeObjectNode> canCreate;

    public static PrivilegeObjectNode from(Long id) {
        PrivilegeObjectNode privilegeObjectNode = new PrivilegeObjectNode();
        privilegeObjectNode.setPrivilegeObjectId(id);
        return privilegeObjectNode;
    }

    public PrivilegeObject toPrivilegeObject() {
        PrivilegeObject privilegeObject = new PrivilegeObject();
        privilegeObject.setId(this.privilegeObjectId);
        return privilegeObject;
    }

}
