package pl.morecraft.dev.settler.security.authorisation;


import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.QPrivilegeObject;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectHierarchy.jTBObjectHierarchy;
import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectPrivilege.jTBObjectPrivilege;

@Service
public class PermissionManagerImplementation implements PermissionManager {

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
        return new JPAQuery(entityManager).from(jTBObjectPrivilege, jTBObjectHierarchy)
                .where(jTBObjectPrivilege.objectFrom.eq(user),
                        jTBObjectPrivilege.operationType.eq(operationType),
                        jTBObjectHierarchy.objectFrom.eq(jTBObjectPrivilege.objectTo),
                        jTBObjectHierarchy.objectTo.eq(target).or(jTBObjectHierarchy.objectTo.isNull())
                )
                .exists();
    }

    @Override
    public BooleanExpression objectFilter(QPrivilegeObject obj, User user, OperationType operationType) {
        return obj.id.eqAny(
                new JPASubQuery().from(jTBObjectPrivilege, jTBObjectHierarchy)
                        .where(jTBObjectPrivilege.objectFrom.eq(user),
                                jTBObjectPrivilege.operationType.eq(operationType),
                                jTBObjectHierarchy.objectFrom.eq(jTBObjectPrivilege.objectTo).or(
                                        jTBObjectPrivilege.objectTo.isNull())
                        )
                        .list(jTBObjectHierarchy.objectTo.id)
        );
    }
}
