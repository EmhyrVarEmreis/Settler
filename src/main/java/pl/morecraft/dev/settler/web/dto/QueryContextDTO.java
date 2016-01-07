package pl.morecraft.dev.settler.web.dto;

import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

public class QueryContextDTO {
    private User user;
    private OperationType operationType;

    public QueryContextDTO() {

    }

    public QueryContextDTO(User user, OperationType operationType) {
        this.user = user;
        this.operationType = operationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
