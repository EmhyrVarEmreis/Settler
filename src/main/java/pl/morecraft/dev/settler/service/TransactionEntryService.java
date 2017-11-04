package pl.morecraft.dev.settler.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.view.TransactionEntryRepository;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.view.QTransactionEntry;
import pl.morecraft.dev.settler.domain.view.TransactionEntry;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.TransactionEntryDTO;
import pl.morecraft.dev.settler.web.dto.TransactionEntryListDTO;
import pl.morecraft.dev.settler.web.misc.TransactionEntryListFilters;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionEntryService extends AbstractService<TransactionEntry, TransactionEntryDTO, TransactionEntryListDTO, TransactionEntryListFilters, QTransactionEntry, Long, TransactionEntryRepository> {

    private final EntityManager entityManager;
    private final TransactionEntryRepository transactionEntryRepository;

    @Autowired
    public TransactionEntryService(EntityManager entityManager, TransactionEntryRepository transactionEntryRepository) {
        this.entityManager = entityManager;
        this.transactionEntryRepository = transactionEntryRepository;
    }

    protected TransactionEntryRepository getUserRepository() {
        return transactionEntryRepository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return false;
    }

    @Override
    protected Class<TransactionEntry> getEntityClass() {
        return TransactionEntry.class;
    }

    @Override
    protected Class<TransactionEntryDTO> getDtoClass() {
        return TransactionEntryDTO.class;
    }

    @Override
    protected Class<TransactionEntryListDTO> getListDtoClass() {
        return TransactionEntryListDTO.class;
    }

    @Override
    protected Class<TransactionEntryListFilters> getListFilterClass() {
        return TransactionEntryListFilters.class;
    }

    @Override
    protected List<BooleanExpression> getPreFilters(QTransactionEntry qTransactionEntry) {
        return CollectionUtils.add(
                new ArrayList<>(),
                getPermissionManager().objectFilter(
                        Security.currentUser(),
                        qTransactionEntry,
                        OperationType.RDM
                )
        );
    }

    @Override
    protected QTransactionEntry getEQ() {
        return QTransactionEntry.transactionEntry;
    }

    @Override
    public ResponseEntity<TransactionEntryDTO> save(TransactionEntryDTO transactionEntryDTO) {
        throw new UnsupportedOperationException("No save operation for views");
    }

}
