package pl.morecraft.dev.settler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.dto.TransactionListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;
import pl.morecraft.dev.settler.web.misc.ListPageConverter;
import pl.morecraft.dev.settler.web.misc.TransactionListFilters;

import javax.inject.Inject;
import java.io.IOException;

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

    public ListPage<TransactionListDTO> getTransactions(Integer page, Integer limit, String sortBy, String filters) {
        QTransaction transaction = QTransaction.transaction;
        Page<Transaction> transactionPage = transactionRepository.findAll(applyFilters(filters, transaction), new QPageRequest(page - 1, limit, applySorting(sortBy, transaction)));
        return new ListPageConverter<Transaction, TransactionListDTO>().convert(transactionPage, TransactionListDTO.class);
    }

    private OrderSpecifier<?> applySorting(String sortBy, QTransaction transaction) {
        boolean isDesc = sortBy.startsWith("-");
        switch (sortBy.substring(1)) {
            case "confirmed":
                return applySorting(transaction.confirmed, isDesc);
            case "value":
                return applySorting(transaction.value, isDesc);
            case "evaluated":
                return applySorting(transaction.evaluated, isDesc);
            case "created":
                return applySorting(transaction.created, isDesc);
            case "type":
                return applySorting(transaction.type, isDesc);
            case "owner":
                return applySorting(transaction.owner.firstName.append(" ").append(transaction.owner.lastName), isDesc);
            default:
                return applySorting(transaction.reference, isDesc);
        }
    }

    private OrderSpecifier<?> applySorting(ComparableExpressionBase<? extends Comparable> sort, boolean b) {
        return b ? sort.desc() : sort.asc();
    }

    private BooleanExpression applyFilters(String filtersJson, QTransaction transaction) {
        BooleanExpression predicate = transaction.id.eq(transaction.id);
        if (filtersJson.length() == 0)
            return predicate;

        TransactionListFilters filters;

        try {
            filters = new ObjectMapper().readValue(filtersJson, TransactionListFilters.class);

            if (filters.getReference() != null) {
                predicate = predicate.and(transaction.reference.contains(filters.getReference()));
            }
            if (filters.getOwner() != null) {
                if (filters.getOwner().contains(" ")) {
                    String[] strings = filters.getOwner().split("\\s+");
                    predicate = predicate.and(
                            transaction.owner.firstName.contains(strings[0]).and(
                                    transaction.owner.lastName.contains(strings[1]))
                    );
                } else {
                    predicate = predicate.and(
                            transaction.owner.firstName.contains(filters.getOwner()).or(
                                    transaction.owner.lastName.contains(filters.getOwner()))
                    );
                }
            }
            if (filters.getValueGt() != null) {
                predicate = predicate.and(transaction.value.gt(filters.getValueGt()));
            }
            if (filters.getValueLt() != null) {
                predicate = predicate.and(transaction.value.lt(filters.getValueLt()));
            }
            if (filters.getValue() != null) {
                predicate = predicate.and(transaction.value.eq(filters.getValue()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return predicate;
    }

}
