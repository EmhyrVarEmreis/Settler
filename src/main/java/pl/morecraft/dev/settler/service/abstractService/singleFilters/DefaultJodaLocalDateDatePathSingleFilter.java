package pl.morecraft.dev.settler.service.abstractService.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DatePath;
import org.joda.time.LocalDate;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

//@Component
//@BaseSingleFilter(types = @SingleFilterApplicableTypes(filterType = LocalDate.class, qObjectType = DatePath.class))
public class DefaultJodaLocalDateDatePathSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((DatePath<LocalDate>) qObjectValue).after(
                (LocalDate) filterValue).and(
                ((DatePath<LocalDate>) qObjectValue).before(
                        ((LocalDate) filterValue).plusDays(1)));
    }

}