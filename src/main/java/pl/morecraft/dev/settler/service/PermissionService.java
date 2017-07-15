package pl.morecraft.dev.settler.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.repository.RoleAssignmentRepository;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.RoleAssignment;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.jtb.QJTBObjectPrivilege;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.converters.ListMapper;
import pl.morecraft.dev.settler.web.dto.RoleAssignmentDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PermissionService {

    @PersistenceContext
    private EntityManager entityManager;

    private final RoleAssignmentRepository roleAssignmentRepository;
    private final PermissionManager permissionManager;
    private final ListMapper listMapper;

    @Autowired
    public PermissionService(
            RoleAssignmentRepository roleAssignmentRepository,
            PermissionManager permissionManager,
            ListMapper listMapper
    ) {
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.permissionManager = permissionManager;
        this.listMapper = listMapper;
    }

    public List<String> getShortSummary() {
        QJTBObjectPrivilege jTBObjectPrivilege = QJTBObjectPrivilege.jTBObjectPrivilege;
        return new JPAQuery<>(entityManager).select(
                jTBObjectPrivilege.operationType.stringValue()
        ).distinct().from(
                jTBObjectPrivilege
        ).where(
                jTBObjectPrivilege.objectFrom.id.eq(Security.currentUser().getId())
        ).fetch();
    }

    public List<RoleAssignmentDTO> getRoleAssignmentsByPrivilegeObject(Long privilegeObjectId) {
        return getRoleAssignmentsByPrivilegeObject(PrivilegeObject.from(privilegeObjectId));
    }

    public List<RoleAssignmentDTO> getRoleAssignmentsByPrivilegeObject(PrivilegeObject privilegeObject) {
        permissionManager.authorize(privilegeObject, OperationType.ADM);
        final List<RoleAssignment> allByTarget = roleAssignmentRepository.findAllByTarget(privilegeObject);
        System.out.println(allByTarget);
        final List<RoleAssignmentDTO> map = listMapper.map(allByTarget, RoleAssignmentDTO.class);
        System.out.println(map);
        return map;
    }

}
