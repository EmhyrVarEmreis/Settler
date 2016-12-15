package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.QRedistribution;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = String.class, qObjectType = ListPath.class))
public class StringListPathRedistributionSingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return JPAExpressions.selectFrom(
                QRedistribution.redistribution
        ).where(
                QRedistribution.redistribution.id.eq(
                        ((ListPath<Redistribution, QRedistribution>) qObjectValue).any().id
                ).and(
                        QRedistribution.redistribution.id.user.firstName
                                .concat(" ")
                                .concat(QRedistribution.redistribution.id.user.lastName)
                                .concat(" (")
                                .concat(QRedistribution.redistribution.id.user.login)
                                .concat(")")
                                .containsIgnoreCase((String) filterValue)
                )
        ).exists();
    }

}
