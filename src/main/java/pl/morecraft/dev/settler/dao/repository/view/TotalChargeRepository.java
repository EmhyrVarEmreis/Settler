package pl.morecraft.dev.settler.dao.repository.view;

import pl.morecraft.dev.settler.configuration.util.ReadOnlyRepository;
import pl.morecraft.dev.settler.domain.view.TotalCharge;

import java.util.List;

public interface TotalChargeRepository extends ReadOnlyRepository<TotalCharge, Long> {

    List<TotalCharge> findAllByUserFromIdOrderByChargeDesc(Long userFromId);

    List<TotalCharge> findAllByUserToIdOrderByChargeDesc(Long userToId);

}
