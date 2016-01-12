package pl.morecraft.dev.settler.domain.jtb;

import pl.morecraft.dev.settler.domain.PrivilegeObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jtb_object_hierarchy")
public class JTBObjectHierarchy implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "prv_object_from")
    private PrivilegeObject objectFrom;

    @Id
    @ManyToOne
    @JoinColumn(name = "prv_object_to")
    private PrivilegeObject objectTo;

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

}
