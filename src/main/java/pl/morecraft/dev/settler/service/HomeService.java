package pl.morecraft.dev.settler.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.web.dto.HomeDTO;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class HomeService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public HomeService(EntityManager entityManager, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<HomeDTO> getHome() {
        QTransaction transaction = QTransaction.transaction;
        return new ResponseEntity<>(
                new HomeDTO(
                        userRepository.count(),
                        transactionRepository.count(),
                        Optional.ofNullable(
                                new JPAQuery<>(entityManager).from(transaction).select(transaction.value.sum()).fetchOne()
                        ).orElse(0.0)
                ),
                HttpStatus.OK
        );
    }

}
