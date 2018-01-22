package pl.morecraft.dev.settler.domain.jtb;

import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "jtb_object_privilege")
@IdClass(JTBObjectPrivilege.JTBObjectPrivilegePK.class)
public class JTBObjectPrivilege implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "prv_object_from")
    private PrivilegeObject objectFrom;

    @Id
    @ManyToOne
    @JoinColumn(name = "prv_object_to")
    private PrivilegeObject objectTo;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

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

    public PrivilegeObject getObjectFrom() {
        return objectFrom;
    }

    public void setObjectFrom(PrivilegeObject objectFrom) {
        this.objectFrom = objectFrom;
    }

    public static class JTBObjectPrivilegePK implements Serializable {

        private Long objectFrom;
        private Long objectTo;
        private OperationType operationType;

        public JTBObjectPrivilegePK() {

        }

        public JTBObjectPrivilegePK(Long objectFrom, Long objectTo, OperationType operationType) {
            this.objectFrom = objectFrom;
            this.objectTo = objectTo;
            this.operationType = operationType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            JTBObjectPrivilegePK that = (JTBObjectPrivilegePK) o;
            return Objects.equals(objectFrom, that.objectFrom) &&
                    Objects.equals(objectTo, that.objectTo) &&
                    operationType == that.operationType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(objectFrom, objectTo, operationType);
        }

    }

}
