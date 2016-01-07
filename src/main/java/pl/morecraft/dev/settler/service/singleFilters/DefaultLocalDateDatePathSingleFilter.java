package pl.morecraft.dev.settler.service.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DatePath;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;

import java.time.LocalDate;

public class DefaultLocalDateDatePathSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((DatePath<LocalDate>) qObjectValue).after(
                (LocalDate) filterValue).and(
                ((DatePath<LocalDate>) qObjectValue).before(
                        ((LocalDate) filterValue).plusDays(1)));
    }

    @Override
    public boolean check(Object filterValue, Object qObjectValue) {
        return (filterValue instanceof LocalDate && qObjectValue instanceof DatePath<?>);
    }

}