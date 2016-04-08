package pl.morecraft.dev.settler.service.abstractService.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(filterType = String.class, qObjectType = StringPath.class))
public class DefaultStringStringPathSingleFilter extends AbstractServiceSingleFilter {

    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((StringPath) qObjectValue).contains((String) filterValue);
    }

}
