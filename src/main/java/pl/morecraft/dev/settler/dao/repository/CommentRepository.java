package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.domain.Comment;

//robi wszystko za siebie (repozytorium). Pobierz element(po id, po czym kolwiek), zapisz element

public interface CommentRepository extends JpaRepository<Comment, Long>, QueryDslPredicateExecutor<Comment> {

}
