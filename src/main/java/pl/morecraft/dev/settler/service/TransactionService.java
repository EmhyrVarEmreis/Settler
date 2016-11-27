package pl.morecraft.dev.settler.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.service.converters.single.ListIntegerConverter;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.dto.TransactionListDTO;
import pl.morecraft.dev.settler.web.misc.TransactionListFilters;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
public class TransactionService extends AbstractService<Transaction, TransactionDTO, TransactionListDTO, TransactionListFilters, QTransaction, Long, TransactionRepository> {

    private final PermissionManager permissionManager;
    private final TransactionRepository repository;

    @Inject
    public TransactionService(PermissionManager permissionManager, TransactionRepository repository) {
        this.permissionManager = permissionManager;
        this.repository = repository;
    }

    @Override
    protected TransactionRepository getRepository() {
        return repository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return true;
    }

    @Override
    protected Class<Transaction> getEntityClass() {
        return Transaction.class;
    }

    @Override
    protected Class<TransactionDTO> getDtoClass() {
        return TransactionDTO.class;
    }

    @Override
    protected Class<TransactionListDTO> getListDtoClass() {
        return TransactionListDTO.class;
    }

    @Override
    protected Class<TransactionListFilters> getListFilterClass() {
        return TransactionListFilters.class;
    }

    @Override
    protected List<BooleanExpression> getPreFilters() {
        return CollectionUtils.add(
                new ArrayList<>(),
                permissionManager.objectFilter(
                        Security.currentUser(),
                        QTransaction.transaction._super,
                        OperationType.RDM
                )
        );
    }

    @Override
    protected QTransaction getEQ() {
        return QTransaction.transaction;
    }

    @Override
    protected Function<Transaction, TransactionDTO> getGetProcessingFunction() {
        return entity -> {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addConverter(new ListIntegerConverter());
            return modelMapper.map(entity, getDtoClass());
        };
    }

}
