package pl.morecraft.dev.settler.service.abstractService.prototype;

import java.util.List;

public interface SingleFiltersPack {

    List<AbstractServiceSingleFilter> getFullEntityAbstractServiceSingleFiltersPack();

    AbstractServiceSingleFilter get(Class<? extends AbstractServiceSingleFilter> clazz);

}
