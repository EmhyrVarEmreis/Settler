package pl.morecraft.dev.settler.service.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import pl.morecraft.dev.settler.domain.QUser;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;

public class CustomStringUserSingleFilter extends AbstractServiceSingleFilter {

    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((QUser) qObjectValue).firstName
                .concat(" ")
                .concat(((QUser) qObjectValue).lastName)
                .concat(" (")
                .concat(((QUser) qObjectValue).login)
                .concat(")")
                .contains((String) filterValue);
    }

    @Override
    public boolean check(Object filterValue, Object qObjectValue) {
        return (filterValue instanceof String && qObjectValue instanceof QUser);
    }

}
