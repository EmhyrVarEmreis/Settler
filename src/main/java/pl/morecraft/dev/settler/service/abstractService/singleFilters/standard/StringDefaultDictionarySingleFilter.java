package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.EnumPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(filterType = DefaultDictionary.class, qObjectType = EnumPath.class))
public class StringDefaultDictionarySingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((EnumPath) qObjectValue).eq(filterValue);
    }

}