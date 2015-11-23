package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {

    User findOneByLogin(String login);
//
//    @Modifying
//    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
//    void setPassword(@Param("userId") Long userId, @Param("password") String password);
}
