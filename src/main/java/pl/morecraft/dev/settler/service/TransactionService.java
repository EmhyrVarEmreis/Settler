package pl.morecraft.dev.settler.service;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.prototype.AbstractService;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.singleFilters.CustomStringUserSingleFilter;
import pl.morecraft.dev.settler.service.singleFilters.DefaultSingleFiltersList;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.dto.TransactionListDTO;
import pl.morecraft.dev.settler.web.misc.TransactionListFilters;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService extends AbstractService<Transaction, TransactionDTO, TransactionListDTO, TransactionListFilters, QTransaction, Long, TransactionRepository> {

    @Inject
    private PermissionManager permissionManager;

    @Inject
    private TransactionRepository repository;

    @Autowired
    private DefaultSingleFiltersList defaultSingleFiltersList;

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
    protected List<AbstractServiceSingleFilter> getAbstractServiceSingleFilters() {
        return CollectionUtils.add(
                defaultSingleFiltersList.getDefaultSingleFiltersList(),
                new CustomStringUserSingleFilter()
        );
    }

    @Override
    protected List<BooleanExpression> getPreFilters() {
        return CollectionUtils.add(
                new ArrayList<>(),
                permissionManager.objectFilter(
                        QTransaction.transaction._super,
                        Security.currentUser(),
                        OperationType.RDM
                )
        );
    }

    @Override
    protected QTransaction getEQ() {
        return QTransaction.transaction;
    }

}
