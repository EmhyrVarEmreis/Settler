package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>, QueryDslPredicateExecutor<Role> {

}
