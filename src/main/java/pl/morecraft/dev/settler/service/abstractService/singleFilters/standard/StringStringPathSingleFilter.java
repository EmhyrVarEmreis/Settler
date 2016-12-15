package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.annotation.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.annotation.SingleFilterApplicableTypes;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = String.class, qObjectType = StringPath.class))
public class StringStringPathSingleFilter extends AbstractServiceSingleFilter {

    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((StringPath) qObjectValue).contains((String) filterValue);
    }

}
