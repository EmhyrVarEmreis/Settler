package pl.morecraft.dev.settler.service.prototype;

import com.mysema.query.types.expr.BooleanExpression;

public abstract class AbstractServiceSingleFilter {

    public abstract BooleanExpression predicate(Object filterValue, Object qObjectValue);

    public BooleanExpression predicate(BooleanExpression predicate, Object filterValue, Object qObjectValue) {
        return predicate.and(predicate(filterValue, qObjectValue));
    }

    public abstract boolean check(Object filterValue, Object qObjectValue);

}
