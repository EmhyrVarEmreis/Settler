package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.RoleAssignment;

import java.util.List;

public interface RoleAssignmentRepository extends JpaRepository<RoleAssignment, Long>, QuerydslPredicateExecutor<RoleAssignment> {

    List<RoleAssignment> findAllByTarget(PrivilegeObject target);

}
