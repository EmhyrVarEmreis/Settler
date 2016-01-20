package pl.morecraft.dev.settler.security.authorisation;


import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.QPrivilegeObject;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectHierarchy.jTBObjectHierarchy;
import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectPrivilege.jTBObjectPrivilege;

@Service
public class PermissionManagerImplementation implements PermissionManager {

    //budowanie zapytań za pomocą encji

    @Inject
    private EntityManager entityManager;

    @Override
    public void authorize(PrivilegeObject source, PrivilegeObject target, OperationType operationType) {
        if (!isAuthorized(source, target, operationType)) {
            throw new AccessDeniedException(
                    "Unable to authorize source [" + source.getId() + "] to target [" + target.getId() + "}"
            );
        }
    }

    @Override
    public boolean isAuthorized(PrivilegeObject source, PrivilegeObject target, OperationType operationType) {
        BooleanExpression expression = jTBObjectHierarchy.objectFrom.id.eq(jTBObjectPrivilege.objectTo.id)
                .and(jTBObjectHierarchy.objectTo.id.eq(target.getId()));
        expression = expression.or(
                jTBObjectPrivilege.objectTo.id.eq(target.getId())
        );
        expression = expression.or(
                jTBObjectPrivilege.objectTo.isNull()
        );
        return new JPAQuery(entityManager).from(jTBObjectPrivilege, jTBObjectHierarchy)
                .where(
                        jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                        (jTBObjectPrivilege.operationType.eq(operationType)),
                        expression
                )
                .exists();
    }

    @Override
    public BooleanExpression objectFilter(PrivilegeObject source, QPrivilegeObject target, OperationType operationType) {
        return target.id.in(
                new JPASubQuery().from(jTBObjectPrivilege, jTBObjectHierarchy)
                        .where(
                                jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                                jTBObjectPrivilege.operationType.eq(operationType),
                                jTBObjectHierarchy.objectFrom.id.eq(jTBObjectPrivilege.objectTo.id)
                        )
                        .list(jTBObjectHierarchy.objectTo.id)
        ).or(
                target.id.in(
                        new JPASubQuery().from(jTBObjectPrivilege)
                                .where(
                                        jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                                        jTBObjectPrivilege.operationType.eq(operationType)
                                )
                                .list(jTBObjectPrivilege.objectTo.id)
                )
        ).or(
                new JPASubQuery().from(jTBObjectPrivilege)
                        .where(
                                jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                                jTBObjectPrivilege.operationType.eq(operationType),
                                jTBObjectPrivilege.objectTo.isNull()
                        )
                        .exists()
        );
    }

}
