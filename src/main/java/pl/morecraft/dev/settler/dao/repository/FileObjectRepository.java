package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.domain.FileObject;

public interface FileObjectRepository extends JpaRepository<FileObject, Long>, QueryDslPredicateExecutor<FileObject> {

    FileObject findOneByName(String name);

}