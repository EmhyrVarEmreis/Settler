package pl.morecraft.dev.settler.domain.jtb;

import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jtb_object_hierarchy")
public class JTBObjectHierarchy implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "object_from")
    private PrivilegeObject objectFrom;

    @Id
    @ManyToOne
    @JoinColumn(name = "object_to")
    private PrivilegeObject objectTo;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    public PrivilegeObject getObjectFrom() {
        return objectFrom;
    }

    public void setObjectFrom(PrivilegeObject objectFrom) {
        this.objectFrom = objectFrom;
    }

    public PrivilegeObject getObjectTo() {
        return objectTo;
    }

    public void setObjectTo(PrivilegeObject objectTo) {
        this.objectTo = objectTo;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

}
