package pl.morecraft.dev.settler.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import javax.inject.Inject;

@Service
@Transactional
public class TransactionService {

    @Inject
    private TransactionRepository transactionRepository;

    public TransactionDTO get(Long userId) {
        ModelMapper mapper = new ModelMapper();
        Transaction user = transactionRepository.findOne(userId);
        return mapper.map(user, TransactionDTO.class);
    }

    public ListPage<TransactionDTO> getTransactions(Integer page, Integer limit, String sort, String filters) {
        return null;
    }
}
