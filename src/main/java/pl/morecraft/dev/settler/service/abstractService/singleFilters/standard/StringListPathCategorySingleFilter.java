package pl.morecraft.dev.settler.service.abstractService.singleFilters.standard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.Category;
import pl.morecraft.dev.settler.domain.QCategory;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.BaseSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.prototype.SingleFilterApplicableTypes;

@Component
@BaseSingleFilter(types = @SingleFilterApplicableTypes(qValueType = String.class, qObjectType = ListPath.class))
public class StringListPathCategorySingleFilter extends AbstractServiceSingleFilter {

    @SuppressWarnings("unchecked")
    @Override
    public BooleanExpression predicate(Object filterValue, Object qObjectValue) {
        return JPAExpressions.selectFrom(
                QCategory.category
        ).where(
                QCategory.category.id.eq(
                        ((ListPath<Category, QCategory>) qObjectValue).any().id
                ).and(
                        QCategory.category.code
                                .concat(" ")
                                .concat(QCategory.category.description)
                                .containsIgnoreCase((String) filterValue)
                )
        ).exists();
    }

}
