package pl.morecraft.dev.settler.service.abstractService.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.DateTimePath;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = {
        @SingleFilterApplicableTypes(filterType = LocalDate.class, qObjectType = DatePath.class),
        @SingleFilterApplicableTypes(filterType = LocalDate.class, qObjectType = DateTimePath.class),
        @SingleFilterApplicableTypes(filterType = LocalDateTime.class, qObjectType = DateTimePath.class),
        @SingleFilterApplicableTypes(filterType = LocalDateTime.class, qObjectType = DatePath.class)
})
public class ExtendedJodaDateAndTimeSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        if (qObjectValue instanceof DatePath) {
            LocalDate localDate;
            if (filterValue instanceof LocalDate) {
                localDate = (LocalDate) filterValue;
            } else if (filterValue instanceof LocalDateTime) {
                localDate = toLocalDate((LocalDateTime) filterValue);
            } else {
                return null;
            }
            return ((DatePath<LocalDate>) qObjectValue).eq(localDate);
        } else if (qObjectValue instanceof DateTimePath) {
            LocalDateTime localDateTime;
            if (filterValue instanceof LocalDate) {
                localDateTime = toLocalDateTime((LocalDate) filterValue);
            } else if (filterValue instanceof LocalDateTime) {
                localDateTime = (LocalDateTime) filterValue;
            } else {
                return null;
            }
            DateTimePath<LocalDateTime> q = (DateTimePath<LocalDateTime>) qObjectValue;
            return q.eq(localDateTime).or(
                    q.between(localDateTime, localDateTime.plusDays(1))
            );
        }
        return null;
    }

    private LocalDate toLocalDate(LocalDateTime localDateTime) {
        return new LocalDate(
                localDateTime
        );
    }

    private LocalDateTime toLocalDateTime(LocalDate localDate) {
        return new LocalDateTime(
                localDate.getYear(),
                localDate.getMonthOfYear(),
                localDate.getDayOfMonth(),
                0,
                0,
                0
        );
    }

}
