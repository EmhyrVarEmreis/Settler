package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = String.class, qObjectType = NumberPath.class))
public class StringNumberPathSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        String filterValueString = (String) filterValue;
        if (filterValueString.startsWith(">")) {
            return ((NumberPath<Double>) qObjectValue).gt(Double.parseDouble(filterValueString.substring(1).replace(" ", "").replace(",", ".")));
        } else if (filterValueString.startsWith("<")) {
            return ((NumberPath<Double>) qObjectValue).lt(Double.parseDouble(filterValueString.substring(1).replace(" ", "").replace(",", ".")));
        } else if (filterValueString.startsWith("=")) {
            return ((NumberPath<Double>) qObjectValue).eq(Double.parseDouble(filterValueString.substring(1).replace(" ", "").replace(",", ".")));
        } else {
            return ((NumberPath<Double>) qObjectValue).eq(Double.parseDouble(filterValueString.replace(" ", "").replace(",", ".")));
        }
    }

}
