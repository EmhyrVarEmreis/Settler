package pl.morecraft.dev.settler.security.authorisation;

import com.querydsl.core.types.dsl.BooleanExpression;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.QPrivilegeObject;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

public interface PermissionManager {

    void authorize(Long targetId, OperationType operationType);

    void authorize(PrivilegeObject target, OperationType operationType);

    void authorize(Long sourceId, Long targetId, OperationType operationType);

    void authorize(PrivilegeObject source, PrivilegeObject target, OperationType operationType);

    boolean isAuthorized(Long targetId, OperationType operationType);

    boolean isAuthorized(PrivilegeObject target, OperationType operationType);

    boolean isAuthorized(Long sourceId, Long targetId, OperationType operationType);

    boolean isAuthorized(PrivilegeObject source, PrivilegeObject target, OperationType operationType);

    BooleanExpression objectFilter(PrivilegeObject source, QPrivilegeObject target, OperationType operationType);

    boolean isGlobalAdmin(Long userId);

    void authorizeGlobalAdmin();

}
