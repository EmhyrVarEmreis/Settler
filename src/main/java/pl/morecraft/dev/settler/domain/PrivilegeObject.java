package pl.morecraft.dev.settler.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "prv_object")
//@Audited
@Inheritance(strategy = InheritanceType.JOINED)
public class PrivilegeObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrivilegeObject)) {
            return false;
        }

        PrivilegeObject that = (PrivilegeObject) o;

        return !(id == null || that.id == null) && id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static PrivilegeObject from(Long id) {
        PrivilegeObject privilegeObject = new PrivilegeObject();
        privilegeObject.setId(id);
        return privilegeObject;
    }

}
