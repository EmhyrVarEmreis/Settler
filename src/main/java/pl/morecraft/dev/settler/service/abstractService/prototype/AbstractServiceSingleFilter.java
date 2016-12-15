package pl.morecraft.dev.settler.service.abstractService.prototype;

import com.querydsl.core.types.dsl.BooleanExpression;
import pl.morecraft.dev.settler.service.abstractService.AnnotationNotPresentException;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.BaseFilter;

import java.lang.reflect.Field;
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

    boolean isApplicable(Object filterValue, Object qObjectValue) {
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

    private boolean isApplicable(SingleFilterApplicableTypes t, Object filterValue, Object qObjectValue) {
        return isApplicable(t.qValueType(), t.qObjectType(), filterValue, qObjectValue);
    }

    private boolean isApplicable(Class<?> filterType, Class<?> qObjectType, Object filterValue, Object qObjectValue) {
        return filterType.isAssignableFrom(filterValue.getClass())
                && qObjectType.isAssignableFrom(qObjectValue.getClass());
    }

    protected static AbstractServiceSingleFilter getAbstractServiceSingleFilterClassFromField(Field field, SingleFiltersPack singleFiltersPack) {
        BaseFilter baseFilter = field.getAnnotation(BaseFilter.class);
        if (baseFilter == null) {
            return null;
        } else {
            return singleFiltersPack.get(baseFilter.value());
        }
    }

}
