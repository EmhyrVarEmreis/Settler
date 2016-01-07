package pl.morecraft.dev.settler.service.singleFilters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class DefaultSingleFiltersList {

    public List<AbstractServiceSingleFilter> getDefaultSingleFiltersList() {
        List<AbstractServiceSingleFilter> list = new ArrayList<>();

        list.add(new DefaultStringStringPathSingleFilter());
        list.add(new DefaultLocalDateDatePathSingleFilter());

        return list;
    }

}
