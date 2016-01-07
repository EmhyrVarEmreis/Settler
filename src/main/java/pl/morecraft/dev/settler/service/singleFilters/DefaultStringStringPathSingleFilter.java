package pl.morecraft.dev.settler.service.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;

public class DefaultStringStringPathSingleFilter extends AbstractServiceSingleFilter {

    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((StringPath) qObjectValue).contains((String) filterValue);
    }

    @Override
    public boolean check(Object filterValue, Object qObjectValue) {
        return (filterValue instanceof String && qObjectValue instanceof StringPath);
    }

}
