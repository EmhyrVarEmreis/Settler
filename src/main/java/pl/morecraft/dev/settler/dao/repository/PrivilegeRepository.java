package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.morecraft.dev.settler.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, QuerydslPredicateExecutor<Privilege> {

}
