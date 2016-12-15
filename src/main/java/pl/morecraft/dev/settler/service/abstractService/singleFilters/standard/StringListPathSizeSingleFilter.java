package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ListPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.annotation.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.annotation.SingleFilterApplicableTypes;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = String.class, qObjectType = ListPath.class))
public class StringListPathSizeSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        String filterValueString = (String) filterValue;
        if (filterValueString.startsWith(">")) {
            return ((ListPath<?, ?>) qObjectValue).size().gt(Integer.parseInt(filterValueString.substring(1)));
        } else if (filterValueString.startsWith("<")) {
            return ((ListPath<?, ?>) qObjectValue).size().lt(Integer.parseInt(filterValueString.substring(1)));
        } else if (filterValueString.startsWith("=")) {
            return ((ListPath<?, ?>) qObjectValue).size().eq(Integer.parseInt(filterValueString.substring(1)));
        } else {
            return ((ListPath<?, ?>) qObjectValue).size().eq(Integer.parseInt(filterValueString));
        }
    }

}
