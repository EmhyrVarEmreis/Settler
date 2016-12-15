package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = DefaultDictionary.class, qObjectType = EnumPath.class))
public class StringDefaultDictionarySingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((EnumPath) qObjectValue).eq(filterValue);
    }

}