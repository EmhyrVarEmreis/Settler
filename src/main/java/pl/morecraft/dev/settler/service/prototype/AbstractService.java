package pl.morecraft.dev.settler.service.prototype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.path.EntityPathBase;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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

    private BooleanExpression applyFilters(String filtersJson, EQ qObject) throws NoSuchFieldException, IllegalAccessException {
        BooleanExpressionWrapper pWrapper = new BooleanExpressionWrapper();
        pWrapper.predicate = qObject.isNotNull();

        for (BooleanExpression preFilter : getPreFilters()) {
            pWrapper.predicate = pWrapper.predicate.and(preFilter);
        }

        if (filtersJson.length() == 0)
            return pWrapper.predicate;

        FL filters;

        try {
            filters = new ObjectMapper().readValue(filtersJson, getListFilterClass());

            Stream.concat(
                    Arrays.stream(filters.getClass().getDeclaredFields()),
                    getExtendedFields(filters)
            ).filter(
                    field -> extractValueFromField(field, filters) != null
            ).forEach(
                    f -> getAbstractServiceSingleFilters()
                            .stream()
                            .filter(filter -> filter.check(
                                    extractValueFromField(f, filters),
                                    extractValueFromField(
                                            f,
                                            extractFieldFromObject(
                                                    qObject,
                                                    f.getName()
                                            )
                                    ))
                            )
                            .forEach(filter -> pWrapper.predicate = filter.predicate(
                                    pWrapper.predicate,
                                    extractValueFromField(f, filters),
                                    extractValueFromField(
                                            f,
                                            extractFieldFromObject(
                                                    qObject,
                                                    f.getName()
                                            )
                                    ))
                            )
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pWrapper.predicate;
    }

    private Stream<Field> getExtendedFields(FL filters) {
        if (getExtendedFilters()) {
            return Arrays.stream(filters.getClass().getSuperclass().getDeclaredFields());
        }
        return Collections.<Field>emptyList().stream();
    }

    private Object extractValueFromField(Field field, Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object extractFieldFromObject(Object clazz, String name) {
        try {
            return clazz.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class BooleanExpressionWrapper {
        private BooleanExpression predicate;
    }

}
