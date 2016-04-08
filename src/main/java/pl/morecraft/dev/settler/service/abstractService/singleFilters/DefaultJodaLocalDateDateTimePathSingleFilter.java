package pl.morecraft.dev.settler.service.abstractService.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DateTimePath;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

//@Component
//@BaseSingleFilter(types = @SingleFilterApplicableTypes(filterType = LocalDate.class, qObjectType = DateTimePath.class))
public class DefaultJodaLocalDateDateTimePathSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        LocalDate localDate = (LocalDate) filterValue;
        LocalDateTime localDateTime = new LocalDateTime(
                localDate.getYear(),
                localDate.getMonthOfYear(),
                localDate.getDayOfMonth(),
                0,
                0,
                0
        );
        return ((DateTimePath<LocalDateTime>) qObjectValue).after(localDateTime)
                .and(((DateTimePath<LocalDateTime>) qObjectValue).before(localDateTime.plusDays(1)))
                .or(((DateTimePath<LocalDateTime>) qObjectValue).eq(localDateTime));
    }

}