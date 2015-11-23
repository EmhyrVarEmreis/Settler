package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.domain.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long>, QueryDslPredicateExecutor<Settlement> {

    Settlement findOneByReference(String reference);

}
