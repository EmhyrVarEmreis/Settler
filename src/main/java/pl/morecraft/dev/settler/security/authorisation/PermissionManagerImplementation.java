package pl.morecraft.dev.settler.security.authorisation;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.*;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.web.dto.UserDTO;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectHierarchy.jTBObjectHierarchy;
import static pl.morecraft.dev.settler.domain.jtb.QJTBObjectPrivilege.jTBObjectPrivilege;

@Service
public class PermissionManagerImplementation implements PermissionManager {

    @PersistenceContext
    private final EntityManager entityManager;

    @Inject
    public PermissionManagerImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void authorize(Long targetId, OperationType operationType) {
        authorize(Security.currentUser().getId(), targetId, operationType);
    }

    @Override
    public void authorize(PrivilegeObject target, OperationType operationType) {
        authorize(target.getId(), operationType);
    }

    @Override
    public void authorize(Long sourceId, Long targetId, OperationType operationType) {
        if (!isAuthorized(sourceId, targetId, operationType)) {
            throw new AccessDeniedException(
                    "Unable to authorize source [" + sourceId + "] to target [" + targetId + "] with operationType [" + operationType.getCode() + "]"
            );
        }
    }

    @Override
    public void authorize(PrivilegeObject source, PrivilegeObject target, OperationType operationType) {
        authorize(source.getId(), target.getId(), operationType);
    }

    @Override
    public boolean isAuthorized(Long targetId, OperationType operationType) {
        return isAuthorized(Security.currentUser().getId(), targetId, operationType);
    }

    @Override
    public boolean isAuthorized(PrivilegeObject target, OperationType operationType) {
        return isAuthorized(target.getId(), operationType);
    }

    @Override
    public boolean isAuthorized(Long sourceId, Long targetId, OperationType operationType) {
        BooleanExpression expression = jTBObjectHierarchy.objectFrom.id.eq(jTBObjectPrivilege.objectTo.id)
                .and(jTBObjectHierarchy.objectTo.id.eq(targetId));
        expression = expression.or(
                jTBObjectPrivilege.objectTo.id.eq(targetId)
        );
        expression = expression.or(
                jTBObjectPrivilege.objectTo.isNull()
        );
        return new JPAQuery<Void>(entityManager).select(Expressions.ONE).from(jTBObjectPrivilege, jTBObjectHierarchy)
                .where(
                        jTBObjectPrivilege.objectFrom.id.eq(sourceId),
                        (jTBObjectPrivilege.operationType.eq(operationType)),
                        expression
                ).fetchFirst() != null;
    }

    @Override
    public boolean isAuthorized(PrivilegeObject source, PrivilegeObject target, OperationType operationType) {
        return isAuthorized(source.getId(), target.getId(), operationType);
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

    @Override
    public boolean isGlobalAdmin(Long userId) {
        QRole qRole = QRole.role;
        QPrivilege qPrivilege = QPrivilege.privilege;
        QRoleAssignment qRoleAssignment = QRoleAssignment.roleAssignment;
        return new JPAQuery<>(entityManager).select(qRole.id)
                .from(qRole)
                .leftJoin(qPrivilege).on(qPrivilege.prvOwner.id.eq(qRole.id))
                .leftJoin(qRoleAssignment).on(qRoleAssignment.role.id.eq(qRole.id))
                .where(
                        qPrivilege.operationType.eq(OperationType.ADM).and(
                                qRoleAssignment.target.isNull()
                        ).and(
                                qRoleAssignment.user.id.eq(userId)
                        )
                )
                .fetchCount() > 0;
    }

    @Override
    public void authorizeGlobalAdmin() {
        final Long userId = Security.currentUser().getId();
        if (!isGlobalAdmin(userId)) {
            throw new AccessDeniedException(
                    "Unable to authorize user [" + userId + "] as global admin"
            );
        }
    }

}
