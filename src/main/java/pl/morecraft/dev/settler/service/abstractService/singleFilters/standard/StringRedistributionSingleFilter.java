package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.QRedistribution;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

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
