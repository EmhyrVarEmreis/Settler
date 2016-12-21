package pl.morecraft.dev.settler.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.RedistributionRepository;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.dto.TransactionListDTO;
import pl.morecraft.dev.settler.web.misc.TransactionListFilters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@Transactional
public class TransactionService extends AbstractService<Transaction, TransactionDTO, TransactionListDTO, TransactionListFilters, QTransaction, Long, TransactionRepository> {

    private final EmailService emailService;
    private final SequenceManager sequenceManager;
    private final PermissionManager permissionManager;
    private final TransactionRepository transactionRepository;
    private final RedistributionRepository redistributionRepository;

    @Autowired
    public TransactionService(EmailService emailService, SequenceManager sequenceManager, PermissionManager permissionManager, TransactionRepository transactionRepository, RedistributionRepository redistributionRepository) {
        this.emailService = emailService;
        this.sequenceManager = sequenceManager;
        this.permissionManager = permissionManager;
        this.transactionRepository = transactionRepository;
        this.redistributionRepository = redistributionRepository;
    }

    protected TransactionRepository getUserRepository() {
        return transactionRepository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return false;
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
    protected UnaryOperator<Transaction> getSaveSavePreProcessingFunction() {
        return transaction -> {
            if (transaction.getOwners() != null) {
                for (Redistribution redistribution : transaction.getOwners()) {
                    redistribution.getId().setParent(transaction);
                    redistribution.getId().setType(RedistributionType.O);
                }
            }
            if (transaction.getContractors() != null) {
                for (Redistribution redistribution : transaction.getContractors()) {
                    redistribution.getId().setParent(transaction);
                    redistribution.getId().setType(RedistributionType.C);
                }
            }
            if (transaction.getType() == null) {
                transaction.setType(TransactionType.NOR);
            }
            if (transaction.getReference() == null) {
                transaction.setReference(sequenceManager.getNextReferenceForTransaction(transaction));
            }
            if (transaction.getCreated() == null) {
                transaction.setCreated(new LocalDateTime());
            }
            if (transaction.getCreator() == null) {
                transaction.setCreator(Security.currentUser());
            }
            return super.getSaveSavePreProcessingFunction().apply(transaction);
        };
    }

    @Override
    protected UnaryOperator<Transaction> getSaveSavePostProcessingFunction() {
        return transaction -> {
            if (!hasId()) {
                emailService.sendNotificationEmailNewTransaction(transaction);
            }
            return super.getSaveSavePostProcessingFunction().apply(transaction);
        };
    }

    @Override
    protected boolean checkIfHasId(TransactionDTO entity) {
        return entity.getId() != null;
    }

}
