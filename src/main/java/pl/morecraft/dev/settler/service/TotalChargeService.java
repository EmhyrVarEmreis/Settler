package pl.morecraft.dev.settler.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.repository.view.TotalChargeRepository;
import pl.morecraft.dev.settler.domain.view.QTotalCharge;
import pl.morecraft.dev.settler.domain.view.TotalCharge;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.TotalChargeDTO;
import pl.morecraft.dev.settler.web.dto.TotalChargeListDTO;
import pl.morecraft.dev.settler.web.misc.TotalChargeListFilters;

import javax.inject.Inject;
import java.util.List;

@Service
public class TotalChargeService extends AbstractService<TotalCharge, TotalChargeDTO, TotalChargeListDTO, TotalChargeListFilters, QTotalCharge, Long, TotalChargeRepository> {

    private final TotalChargeRepository totalChargeRepository;

    @Inject
    public TotalChargeService(TotalChargeRepository totalChargeRepository) {
        this.totalChargeRepository = totalChargeRepository;
    }

    protected TotalChargeRepository getUserRepository() {
        return totalChargeRepository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return true;
    }

    @Override
    protected Class<TotalCharge> getEntityClass() {
        return TotalCharge.class;
    }

    @Override
    protected Class<TotalChargeDTO> getDtoClass() {
        return TotalChargeDTO.class;
    }

    @Override
    protected Class<TotalChargeListDTO> getListDtoClass() {
        return TotalChargeListDTO.class;
    }

    @Override
    protected Class<TotalChargeListFilters> getListFilterClass() {
        return TotalChargeListFilters.class;
    }

    @Override
    protected QTotalCharge getEQ() {
        return QTotalCharge.totalCharge;
    }

    public ResponseEntity<List<TotalChargeDTO>> getByUserFromId(Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<TotalCharge> totalChargeList = getUserRepository().findAllByUserFromId(id);

        List<TotalChargeDTO> totalChargeDTOList = getListPageConverter().convert(totalChargeList, TotalChargeDTO.class);

        return new ResponseEntity<>(totalChargeDTOList, HttpStatus.OK);
    }

    public ResponseEntity<List<TotalChargeDTO>> getByUserToId(Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<TotalCharge> totalChargeList = getUserRepository().findAllByUserToId(id);

        List<TotalChargeDTO> totalChargeDTOList = getListPageConverter().convert(totalChargeList, TotalChargeDTO.class);

        return new ResponseEntity<>(totalChargeDTOList, HttpStatus.OK);
    }

}
