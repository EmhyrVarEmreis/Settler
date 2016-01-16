package pl.morecraft.dev.settler.security.authorisation;

import com.mysema.query.types.expr.BooleanExpression;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.QPrivilegeObject;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

public interface PermissionManager {

    //void authorize(PrivilegeObject target, OperationType operationType);

    void authorize(PrivilegeObject source, PrivilegeObject target, OperationType operationType);


    //boolean isAuthorized(PrivilegeObject target, OperationType operationType);

    boolean isAuthorized(PrivilegeObject source, PrivilegeObject target, OperationType operationType);


    BooleanExpression objectFilter(QPrivilegeObject source, PrivilegeObject target, OperationType operationType);

}
