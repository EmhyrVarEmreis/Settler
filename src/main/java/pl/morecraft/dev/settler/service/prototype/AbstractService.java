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

/**
 * @param <Entity>           Entity
 * @param <EntityDTO>        Entity Data Transfer Object (DTO)
 * @param <ListDTO>          Entity List Data Transfer Object (DTO)
 * @param <ListFilters>      List filter
 * @param <QEntity>          Entity Q-Class
 * @param <EntityID>         Entity ID
 * @param <EntityRepository> Entity repository
 */
public abstract class AbstractService<
        Entity,
        EntityDTO,
        ListDTO,
        ListFilters,
        QEntity extends EntityPathBase<Entity>,
        EntityID extends Serializable,
        EntityRepository extends JpaRepository<Entity, EntityID> & QueryDslPredicateExecutor<Entity>
        > {

    public EntityDTO get(EntityID id) {
        ModelMapper mapper = new ModelMapper();
        Entity user = getRepository().findOne(id);
        return mapper.map(user, getDtoClass());
    }

    public Boolean save(EntityDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Entity entity = mapper.map(dto, getEntityClass());
        getRepository().save(entity);
        return Boolean.TRUE;
    }

    public ListPage<ListDTO> get(Integer page, Integer limit, String sortBy, String filters) {
        QEntity user = getEQ();
        Page<Entity> userPage = null;
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
        return new ListPageConverter<Entity, ListDTO>().convert(userPage, getListDtoClass());
    }

    protected abstract EntityRepository getRepository();

    protected abstract Boolean isFilterClassExtended();

    protected abstract Class<Entity> getEntityClass();

    protected abstract Class<EntityDTO> getDtoClass();

    protected abstract Class<ListDTO> getListDtoClass();

    protected abstract Class<ListFilters> getListFilterClass();

    protected List<AbstractServiceSingleFilter> getAbstractServiceSingleFilters() {
        return Collections.emptyList();
    }

    protected List<BooleanExpression> getPreFilters() {
        return Collections.emptyList();
    }

    protected abstract QEntity getEQ();

    private OrderSpecifier<?> applySorting(String sortBy, QEntity qObject) throws NoSuchFieldException, IllegalAccessException {
        return applySortingSupporter(
                sortBy.startsWith("-"),
                ((ComparableExpressionBase<?>) qObject.getClass().getDeclaredField(sortBy.substring(1)).get(qObject))
        );
    }

    private OrderSpecifier<?> applySortingSupporter(boolean isDesc, ComparableExpressionBase<?> comparableExpressionBase) {
        return isDesc ? comparableExpressionBase.desc() : comparableExpressionBase.asc();
    }

    private BooleanExpression applyFilters(String filtersJson, QEntity qObject) throws NoSuchFieldException, IllegalAccessException {
        BooleanExpressionWrapper pWrapper = new BooleanExpressionWrapper();
        pWrapper.predicate = qObject.isNotNull();

        for (BooleanExpression preFilter : getPreFilters()) {
            pWrapper.predicate = pWrapper.predicate.and(preFilter);
        }

        if (filtersJson.length() == 0)
            return pWrapper.predicate;

        ListFilters filters;

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

    private Stream<Field> getExtendedFields(ListFilters filters) {
        if (isFilterClassExtended()) {
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
