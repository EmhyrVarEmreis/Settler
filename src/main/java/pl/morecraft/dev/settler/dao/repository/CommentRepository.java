package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.domain.Comment;
import pl.morecraft.dev.settler.domain.PrivilegeObject;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, QueryDslPredicateExecutor<Comment> {

    List<Comment> findAllByObject(PrivilegeObject object);

}
