package pl.morecraft.dev.settler.service.abstractService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFiltersPack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class BaseSingleFiltersPack implements SingleFiltersPack {

    private Map<Class<? extends AbstractServiceSingleFilter>, AbstractServiceSingleFilter> abstractServiceSingleFilterMap;

    @Inject
    public BaseSingleFiltersPack(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(BaseSingleFilter.class);
        abstractServiceSingleFilterMap = new HashMap<>(beans.size());
        for (Object o : beans.values()) {
            if (o instanceof AbstractServiceSingleFilter) {
                abstractServiceSingleFilterMap.put(((AbstractServiceSingleFilter) o).getClass(), (AbstractServiceSingleFilter) o);
            } else {
                throw new ClassCastException("Unable to cast " + o.getClass() + " to AbstractConverter");
            }
        }
    }

    @Override
    public synchronized List<AbstractServiceSingleFilter> getFullEntityAbstractServiceSingleFiltersPack() {
        return new ArrayList<>(abstractServiceSingleFilterMap.values());
    }

    @Override
    public AbstractServiceSingleFilter get(Class<? extends AbstractServiceSingleFilter> clazz) {
        return abstractServiceSingleFilterMap.get(clazz);
    }

}
