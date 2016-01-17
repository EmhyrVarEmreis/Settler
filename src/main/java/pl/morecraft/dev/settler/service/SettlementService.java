package pl.morecraft.dev.settler.service;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.util.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.SettlementRepository;
import pl.morecraft.dev.settler.domain.QSettlement;
import pl.morecraft.dev.settler.domain.Settlement;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.converters.ListIntegerConverter;
import pl.morecraft.dev.settler.service.prototype.AbstractService;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.singleFilters.CustomStringUserSingleFilter;
import pl.morecraft.dev.settler.service.singleFilters.DefaultSingleFiltersList;
import pl.morecraft.dev.settler.web.dto.SettlementDTO;
import pl.morecraft.dev.settler.web.dto.SettlementListDTO;
import pl.morecraft.dev.settler.web.misc.SettlementListFilters;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
public class SettlementService extends AbstractService<Settlement, SettlementDTO, SettlementListDTO, SettlementListFilters, QSettlement, Long, SettlementRepository> {

    @Inject
    private PermissionManager permissionManager;

    @Inject
    private SettlementRepository repository;

    @Autowired
    private DefaultSingleFiltersList defaultSingleFiltersList;

    @Override
    protected SettlementRepository getRepository() {
        return repository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return true;
    }

    @Override
    protected Class<Settlement> getEntityClass() {
        return Settlement.class;
    }

    @Override
    protected Class<SettlementDTO> getDtoClass() {
        return SettlementDTO.class;
    }

    @Override
    protected Class<SettlementListDTO> getListDtoClass() {
        return SettlementListDTO.class;
    }

    @Override
    protected Class<SettlementListFilters> getListFilterClass() {
        return SettlementListFilters.class;
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
                        Security.currentUser(),
                        QSettlement.settlement._super,
                        OperationType.RDM
                )
        );
    }

    @Override
    protected QSettlement getEQ() {
        return QSettlement.settlement;
    }

    @Override
    protected Function<Settlement, SettlementDTO> getGetProcessingFunction() {
        return entity -> {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addConverter(new ListIntegerConverter());
            return modelMapper.map(entity, getDtoClass());
        };
    }

}