package pl.morecraft.dev.settler.service.abstractService.prototype;

import com.querydsl.core.types.dsl.BooleanExpression;
import pl.morecraft.dev.settler.service.abstractService.AnnotationNotPresentException;

import java.util.function.BiFunction;

public abstract class AbstractServiceSingleFilter {

    public abstract BooleanExpression predicate(Object filterValue, Object qObjectValue);

    public BooleanExpression predicateAnd(BooleanExpression predicate, Object filterValue, Object qObjectValue) {
        return predicate(predicate, filterValue, qObjectValue, BooleanExpression::and);
    }

    public BooleanExpression predicateOr(BooleanExpression predicate, Object filterValue, Object qObjectValue) {
        return predicate(predicate, filterValue, qObjectValue, BooleanExpression::or);
    }

    public BooleanExpression predicate(BooleanExpression predicate, Object filterValue, Object qObjectValue, BiFunction<BooleanExpression, BooleanExpression, BooleanExpression> op) {
        return op.apply(predicate, predicate(filterValue, qObjectValue));
    }

    public boolean isApplicable(Object filterValue, Object qObjectValue) {
        BaseSingleFilter baseSingleFilter = this.getClass().getAnnotation(BaseSingleFilter.class);
        if (baseSingleFilter == null) {
            throw new AnnotationNotPresentException("Annotation " + BaseSingleFilter.class.toString() + " is required on " + this.getClass().getCanonicalName());
        }
        for (SingleFilterApplicableTypes t : baseSingleFilter.types()) {
            if (isApplicable(t, filterValue, qObjectValue)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isApplicable(SingleFilterApplicableTypes t, Object filterValue, Object qObjectValue) {
        return isApplicable(t.filterType(), t.qObjectType(), filterValue, qObjectValue);
    }

    protected boolean isApplicable(Class<?> filterType, Class<?> qObjectType, Object filterValue, Object qObjectValue) {
        return filterType.isAssignableFrom(filterValue.getClass())
                && qObjectType.isAssignableFrom(qObjectValue.getClass());
    }

}
