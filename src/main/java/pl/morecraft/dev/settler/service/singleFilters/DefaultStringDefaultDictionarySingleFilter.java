package pl.morecraft.dev.settler.service.singleFilters;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.EnumPath;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;
import pl.morecraft.dev.settler.service.prototype.AbstractServiceSingleFilter;

public class DefaultStringDefaultDictionarySingleFilter extends AbstractServiceSingleFilter {

    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((EnumPath) qObjectValue).eq(filterValue);
    }

    @Override
    public boolean check(Object filterValue, Object qObjectValue) {
        return (filterValue instanceof DefaultDictionary && qObjectValue instanceof EnumPath);
    }

}