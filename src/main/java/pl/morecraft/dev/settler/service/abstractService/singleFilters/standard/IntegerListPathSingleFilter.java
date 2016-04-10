package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.ListPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(filterType = Integer.class, qObjectType = ListPath.class))
public class IntegerListPathSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((ListPath<?, ?>) qObjectValue).size().eq((Integer) filterValue);
    }

}
