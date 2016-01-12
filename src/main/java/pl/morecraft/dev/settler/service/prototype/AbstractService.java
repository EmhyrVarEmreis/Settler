package pl.morecraft.dev.settler.service.prototype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.path.EntityPathBase;
import org.apache.commons.lang.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.morecraft.dev.settler.web.misc.ListPage;
import pl.morecraft.dev.settler.web.misc.ListPageConverter;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/*
E - Entity
EQ - Entity Q
I - ID
R - Repo
D - DTO
LD - List DTO
FL - List Filters
 */

public abstract class AbstractService<
        E,
        D,
        DL,
        FL,
        EQ extends EntityPathBase<E>,
        I extends Serializable,
        R extends JpaRepository<E, I> & QueryDslPredicateExecutor<E>
        > {

    public D get(I id) {
        ModelMapper mapper = new ModelMapper();
        E user = getRepository().findOne(id);
        return mapper.map(user, getDtoClass());
    }

    public Boolean save(D dto) {
        ModelMapper mapper = new ModelMapper();
        E entity = mapper.map(dto, getEntityClass());
        getRepository().save(entity);
        return Boolean.TRUE;
    }

    public ListPage<DL> get(Integer page, Integer limit, String sortBy, String filters) {
        EQ user = getEQ();
        Page<E> userPage = null;
        try {
            userPage = getRepository().findAll(
                    applyFilters(filters, user),
                    new QPageRequest(page - 1,
                            limit,
                            applySorting(sortBy, user)
                    )
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ListPageConverter<E, DL>().convert(userPage, getListDtoClass());
    }

    protected abstract R getRepository();

    protected abstract Boolean getExtendedFilters();

    protected abstract Class<E> getEntityClass();

    protected abstract Class<D> getDtoClass();

    protected abstract Class<DL> getListDtoClass();

    protected abstract Class<FL> getListFilterClass();

    protected List<AbstractServiceSingleFilter> getAbstractServiceSingleFilters() {
        return Collections.emptyList();
    }

    protected List<BooleanExpression> getPreFilters() {
        return Collections.emptyList();
    }

    protected abstract EQ getEQ();

    private OrderSpecifier<?> applySorting(String sortBy, EQ qObject) throws NoSuchFieldException, IllegalAccessException {
        return applySortingSupporter(
                sortBy.startsWith("-"),
                ((ComparableExpressionBase<?>) qObject.getClass().getDeclaredField(sortBy.substring(1)).get(qObject))
        );
    }

    private OrderSpecifier<?> applySortingSupporter(boolean isDesc, ComparableExpressionBase<?> comparableExpressionBase) {
        return isDesc ? comparableExpressionBase.desc() : comparableExpressionBase.asc();
    }

    private BooleanExpression additionalFilters(FL filters, EQ qObject) {
        return qObject.eq(qObject);
    }


    private BooleanExpression applyFilters(String filtersJson, EQ qObject) throws NoSuchFieldException, IllegalAccessException {
        BooleanExpression predicate = qObject.isNotNull();

        for (BooleanExpression preFilter : getPreFilters()) {
            predicate.and(preFilter);
        }

        if (filtersJson.length() == 0)
            return predicate;

        FL filters;

        try {
            filters = new ObjectMapper().readValue(filtersJson, getListFilterClass());

            predicate = additionalFilters(filters, qObject);

            Field[] filterFields = filters.getClass().getDeclaredFields();

            if (getExtendedFilters()) {
                filterFields = (Field[]) ArrayUtils.addAll(filterFields, filters.getClass().getSuperclass().getDeclaredFields());
            }

            for (Field f : filterFields) {
                f.setAccessible(true);

                Object value = f.get(filters);

                if (value == null) {
                    continue;
                }

                Object qObjectFieldValue = qObject.getClass().getDeclaredField(f.getName()).get(qObject);

                for (AbstractServiceSingleFilter singleFilter : getAbstractServiceSingleFilters()) {
                    if (singleFilter.check(value, qObjectFieldValue)) {
                        predicate = singleFilter.predicate(predicate, value, qObjectFieldValue);
                    }
                }

                f.setAccessible(false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return predicate;
    }
}