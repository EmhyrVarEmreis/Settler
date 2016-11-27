package pl.morecraft.dev.settler.security.authorisation;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
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

    private final EntityManager entityManager;

    @Inject
    public PermissionManagerImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
        return new JPAQuery<Void>(entityManager).select(Expressions.ONE).from(jTBObjectPrivilege, jTBObjectHierarchy)
                .where(
                        jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                        (jTBObjectPrivilege.operationType.eq(operationType)),
                        expression
                ).fetchFirst() != null;
    }

    @Override
    public BooleanExpression objectFilter(PrivilegeObject source, QPrivilegeObject target, OperationType operationType) {
        return target.id.in(
                JPAExpressions.selectFrom(jTBObjectHierarchy)
                        .where(
                                jTBObjectHierarchy.objectFrom.id.in(
                                        JPAExpressions.selectFrom(jTBObjectPrivilege)
                                                .from(jTBObjectPrivilege)
                                                .where(
                                                        jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                                                        jTBObjectPrivilege.operationType.eq(operationType)
                                                ).select(jTBObjectPrivilege.objectTo.id)
                                )
                        ).select(jTBObjectHierarchy.objectTo.id)
        ).or(
                target.id.in(
                        JPAExpressions.selectFrom(jTBObjectPrivilege)
                                .where(
                                        jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                                        jTBObjectPrivilege.operationType.eq(operationType)
                                )
                                .select(jTBObjectPrivilege.objectTo.id)
                )
        ).or(
                JPAExpressions.selectFrom(jTBObjectPrivilege)
                        .where(
                                jTBObjectPrivilege.objectFrom.id.eq(source.getId()),
                                jTBObjectPrivilege.operationType.eq(operationType),
                                jTBObjectPrivilege.objectTo.isNull()
                        )
                        .exists()
        );
    }

}
