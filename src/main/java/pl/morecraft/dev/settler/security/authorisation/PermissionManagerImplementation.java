package pl.morecraft.dev.settler.security.authorisation;


import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.security.access.AccessDeniedException;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectHierarchy.jTBObjectHierarchy;
import static pl.morecraft.dev.settler.domain.jtb.QJTBUserObject.jTBUserObject;

public class PermissionManagerImplementation implements PermissionMaganer {

    @Inject
    private EntityManager entityManager;

    @Override
    public void authorize(User user, PrivilegeObject target, OperationType operationType) {
        if (!isAuthorized(user, target, operationType)) {
            throw new AccessDeniedException(
                    "Unable to authorize user [" + user.getId() + "] to object [" + target.getId() + "}"
            );
        }
    }

    @Override
    public boolean isAuthorized(User user, PrivilegeObject target, OperationType operationType) {
        return new JPAQuery(entityManager).from(jTBUserObject, jTBObjectHierarchy)
                .where(jTBUserObject.userFrom.eq(user),
                        jTBUserObject.operationType.eq(operationType),
                        jTBObjectHierarchy.objectFrom.eq(jTBUserObject.objectTo),
                        jTBObjectHierarchy.objectTo.eq(target),
                        jTBObjectHierarchy.operationType.eq(operationType).or(jTBObjectHierarchy.operationType.isNull())
                )
                .exists();
    }

}
