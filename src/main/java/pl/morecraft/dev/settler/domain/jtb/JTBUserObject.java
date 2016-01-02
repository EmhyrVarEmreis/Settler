package pl.morecraft.dev.settler.domain.jtb;

import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jtb_user_object")
public class JTBUserObject implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_from")
    private User userFrom;

    @Id
    @ManyToOne
    @JoinColumn(name = "object_to")
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

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

}
