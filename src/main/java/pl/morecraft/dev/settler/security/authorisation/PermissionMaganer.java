package pl.morecraft.dev.settler.security.authorisation;

import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

public interface PermissionMaganer {

    //void authorize(PrivilegeObject target, OperationType operationType);

    void authorize(User user, PrivilegeObject target, OperationType operationType);


    //boolean isAuthorized(PrivilegeObject target, OperationType operationType);

    boolean isAuthorized(User user, PrivilegeObject target, OperationType operationType);

}
