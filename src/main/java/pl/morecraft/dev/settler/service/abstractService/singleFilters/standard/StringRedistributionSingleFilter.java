package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.QRedistribution;
import pl.morecraft.dev.settler.service.abstractService.annotation.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.annotation.SingleFilterApplicableTypes;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = String.class, qObjectType = QRedistribution.class))
public class StringRedistributionSingleFilter extends AbstractServiceSingleFilter {

    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return ((QRedistribution) qObjectValue).id.user.firstName
                .concat(" ")
                .concat(((QRedistribution) qObjectValue).id.user.lastName)
                .concat(" (")
                .concat(((QRedistribution) qObjectValue).id.user.login)
                .concat(")")
                .contains((String) filterValue);
    }

}
