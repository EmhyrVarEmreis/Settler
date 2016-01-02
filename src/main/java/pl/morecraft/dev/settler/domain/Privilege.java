package pl.morecraft.dev.settler.domain;

import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.persistence.*;

@Entity
@Table(name = "prv_privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prv_object")
    private PrivilegeObject prvObject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prv_owner")
    private PrivilegeObject prvOwner;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privilege privilege = (Privilege) o;

        return id.equals(privilege.id)
                && operationType.equals(privilege.operationType)
                && prvObject.equals(privilege.prvObject)
                && prvOwner.equals(privilege.prvOwner);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + prvObject.hashCode();
        result = 31 * result + prvOwner.hashCode();
        result = 31 * result + operationType.hashCode();
        return result;
    }

}
