package pl.morecraft.dev.settler.service.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.ListPath;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;

public class DefaultIntegerListPathSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((ListPath<?, ?>) qObjectValue).size().eq((Integer) filterValue);
    }

    @Override
    public boolean check(Object filterValue, Object qObjectValue) {
        return (filterValue instanceof Integer && qObjectValue instanceof ListPath<?, ?>);
    }

}
