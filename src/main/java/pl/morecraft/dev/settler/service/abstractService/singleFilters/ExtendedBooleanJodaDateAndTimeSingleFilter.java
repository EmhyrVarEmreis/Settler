package pl.morecraft.dev.settler.service.abstractService.singleFilters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.annotation.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.annotation.SingleFilterApplicableTypes;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

@Component
@BaseSingleFilter(types = {
        @SingleFilterApplicableTypes(qValueType = Boolean.class, qObjectType = DatePath.class),
        @SingleFilterApplicableTypes(qValueType = Boolean.class, qObjectType = DateTimePath.class)
})
public class ExtendedBooleanJodaDateAndTimeSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        if (filterValue instanceof Boolean) {
            Boolean filterValueBoolean = (Boolean) filterValue;
            if (qObjectValue instanceof DatePath) {
                if (filterValueBoolean) {
                    return ((DatePath<LocalDate>) qObjectValue).isNotNull();
                } else {
                    return ((DatePath<LocalDate>) qObjectValue).isNull();
                }
            } else if (qObjectValue instanceof DateTimePath) {
                if (filterValueBoolean) {
                    return ((DateTimePath<LocalDateTime>) qObjectValue).isNotNull();
                } else {
                    return ((DateTimePath<LocalDateTime>) qObjectValue).isNull();
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
