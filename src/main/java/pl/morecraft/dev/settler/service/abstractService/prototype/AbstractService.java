package pl.morecraft.dev.settler.service.abstractService.prototype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.morecraft.dev.settler.service.converters.ListPageConverter;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConvertersPack;
import pl.morecraft.dev.settler.web.misc.ListPage;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
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

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private EntityConvertersPack entityConvertersPack;

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private SingleFiltersPack singleFiltersPack;

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private ListPageConverter listPageConverter;

    private boolean hasId;

    public ResponseEntity<EntityDTO> get(EntityID id) {
        Entity entity = getUserRepository().findOne(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!getGetAuthorisationPredicate().test(entity)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!getGetPreValidationPredicate().test(entity)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }

        EntityDTO entityDTO = getGetProcessingFunction().apply(
                getGetPreProcessingFunction().apply(entity)
        );

        if (!getGetPostValidationPredicate().test(entityDTO)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        entityDTO = getGetPostProcessingFunction().apply(entityDTO);

        return new ResponseEntity<>(entityDTO, HttpStatus.OK);
    }

    public ResponseEntity<EntityDTO> save(EntityDTO entityDTO) {
        if (!getSaveAuthorisationPredicate().test(entityDTO)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!getSavePreValidationPredicate().test(entityDTO)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }

        setHasId(checkIfHasId(entityDTO));

        Entity entity = getSavePostProcessingFunction().apply(
                getSaveConvertingFunction().apply(
                        getSavePreProcessingFunction().apply(entityDTO)
                )
        );

        if (!getSavePostValidationPredicate().test(entity)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ((entity = getSaveSaveFunction().apply(getSaveSavePreProcessingFunction().apply(entity))) == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        getSaveSavePostProcessingFunction().apply(entity);

        return new ResponseEntity<>(getGetProcessingFunction().apply(entity), HttpStatus.OK);
    }

    public ListPage<ListDTO> get(Integer page, Integer limit, String sortBy, String filters) {
        return get(page, limit, sortBy, filters, true);
    }

    public ListPage<ListDTO> get(Integer page, Integer limit, String sortBy, String filters, boolean isAnd) {
        QEntity user = getEQ();
        Page<Entity> entityPage = null;
        try {
            entityPage = getUserRepository().findAll(
                    applyFilters(filters, user, isAnd),
                    new QPageRequest(page - 1,
                            limit,
                            applySorting(sortBy, user)
                    )
            );
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return listPageConverter.convert(entityPage, getListDtoClass());
    }

    public List<EntityDTO> findAll() {
        List<Entity> entityDTOList = getUserRepository().findAll();
        return listPageConverter.convert(entityDTOList, getDtoClass());
    }

    protected abstract EntityRepository getUserRepository();

    protected abstract Boolean isFilterClassExtended();

    protected abstract Class<Entity> getEntityClass();

    protected abstract Class<EntityDTO> getDtoClass();

    protected abstract Class<ListDTO> getListDtoClass();

    protected abstract Class<ListFilters> getListFilterClass();

    protected Predicate<EntityDTO> getSavePreValidationPredicate() {
        return entityDTO -> true;
    }

    protected Predicate<Entity> getSavePostValidationPredicate() {
        return entityDTO -> true;
    }

    protected Predicate<EntityDTO> getSaveAuthorisationPredicate() {
        return entityDTO -> true;
    }

    protected UnaryOperator<Entity> getSavePostProcessingFunction() {
        return entity -> entity;
    }

    protected Function<EntityDTO, Entity> getSaveConvertingFunction() {
        return entityDTO -> entityConvertersPack.getPreparedModelMapper().map(entityDTO, getEntityClass());
    }

    protected UnaryOperator<EntityDTO> getSavePreProcessingFunction() {
        return entityDTO -> entityDTO;
    }

    protected Predicate<Entity> getGetPreValidationPredicate() {
        return entity -> true;
    }

    protected Predicate<EntityDTO> getGetPostValidationPredicate() {
        return entityDTO -> true;
    }

    protected Predicate<Entity> getGetAuthorisationPredicate() {
        return entity -> true;
    }

    protected UnaryOperator<EntityDTO> getGetPostProcessingFunction() {
        return entityDTO -> entityDTO;
    }

    protected Function<Entity, EntityDTO> getGetProcessingFunction() {
        return entity -> entityConvertersPack.getPreparedModelMapper().map(entity, getDtoClass());
    }

    protected UnaryOperator<Entity> getGetPreProcessingFunction() {
        return entity -> entity;
    }

    protected UnaryOperator<Entity> getSaveSavePreProcessingFunction() {
        return entity -> entity;
    }

    protected UnaryOperator<Entity> getSaveSavePostProcessingFunction() {
        return entity -> entity;
    }

    protected UnaryOperator<Entity> getSaveSaveFunction() {
        return entity -> getUserRepository().save(entity);
    }

    protected List<AbstractServiceSingleFilter> getAbstractServiceSingleFilters() {
        return singleFiltersPack.getFullEntityAbstractServiceSingleFiltersPack();
    }

    protected List<BooleanExpression> getPreFilters() {
        return Collections.emptyList();
    }

    protected List<AbstractConverter> getConverters() {
        return entityConvertersPack.getFullEntityConvertersPack();
    }

    protected EntityConvertersPack getEntityConvertersPack() {
        return entityConvertersPack;
    }

    protected SingleFiltersPack getSingleFiltersPack() {
        return singleFiltersPack;
    }

    protected ListPageConverter getListPageConverter() {
        return listPageConverter;
    }

    protected ComparableExpressionBase<?> getComparableExpressionBase(String fieldName, QEntity qObject) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String[] t = fieldName.split("\\.");
        Object o = qObject;
        for (String fieldNameTmp : t) {
            Field f = o.getClass().getField(fieldNameTmp);
            f.setAccessible(true);
            o = f.get(o);
        }
        if (o instanceof ComparableExpressionBase) {
            return (ComparableExpressionBase<?>) o;
        } else {
            return null;
        }
    }

    protected abstract QEntity getEQ();

    protected boolean checkIfHasId(EntityDTO entity) {
        return false;
    }

    protected boolean hasId() {
        return hasId;
    }

    private void setHasId(boolean hasId) {
        this.hasId = hasId;
    }

    private OrderSpecifier<?> applySorting(String sortBy, QEntity qObject) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return applySortingSupporter(
                sortBy.startsWith("-"),
                getComparableExpressionBase(sortBy.substring(1), qObject)
        );
    }

    private OrderSpecifier<?> applySortingSupporter(boolean isDesc, ComparableExpressionBase<?> comparableExpressionBase) {
        return isDesc ? comparableExpressionBase.desc() : comparableExpressionBase.asc();
    }

    private BooleanExpression applyFilters(String filtersJson, QEntity qObject, boolean isAnd) throws NoSuchFieldException, IllegalAccessException {
        BooleanExpressionWrapper pWrapper = new BooleanExpressionWrapper();

        if (isAnd) {
            pWrapper.predicate = qObject.isNotNull();
        } else {
            pWrapper.predicate = qObject.ne(qObject);
        }

        for (BooleanExpression preFilter : getPreFilters()) {
            pWrapper.predicate = pWrapper.predicate.and(preFilter);
        }

        if (filtersJson.length() == 0) {
            return pWrapper.predicate;
        }

        ListFilters filters;

        try {
            filters = new ObjectMapper().readValue(filtersJson, getListFilterClass());

            Stream.concat(
                    Arrays.stream(filters.getClass().getDeclaredFields()),
                    getExtendedFields(filters)
            ).filter(
                    field -> extractValueFromField(field, filters) != null
            ).forEach(
                    field -> {
                        AbstractServiceSingleFilter abstractServiceSingleFilter = AbstractServiceSingleFilter.getAbstractServiceSingleFilterClassFromField(field, singleFiltersPack);
                        if (abstractServiceSingleFilter == null) {
                            getAbstractServiceSingleFilters()
                                    .stream()
                                    .filter(filter -> filter.isApplicable(
                                            extractValueFromField(field, filters),
                                            extractValueFromField(
                                                    extractFieldFromObject(
                                                            qObject,
                                                            field.getName()
                                                    ),
                                                    qObject
                                            ))
                                    )
                                    .findFirst()
                                    .ifPresent(
                                            filter -> pWrapper.predicate = apply(
                                                    pWrapper, filter, field, filters, qObject, isAnd
                                            )
                                    );
                        } else {
                            pWrapper.predicate = apply(
                                    pWrapper, abstractServiceSingleFilter, field, filters, qObject, isAnd
                            );
                        }

                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pWrapper.predicate;
    }

    private BooleanExpression apply(BooleanExpressionWrapper pWrapper, AbstractServiceSingleFilter filter, Field field, ListFilters filters, QEntity qObject, boolean isAnd) {
        return pWrapper.predicate = filter.predicate(
                pWrapper.predicate,
                extractValueFromField(field, filters),
                extractValueFromField(
                        extractFieldFromObject(
                                qObject,
                                field.getName()
                        ),
                        qObject
                ),
                isAnd ? BooleanExpression::and : BooleanExpression::or
        );
    }

    private Stream<Field> getExtendedFields(ListFilters filters) {
        if (isFilterClassExtended()) {
            return Arrays.stream(filters.getClass().getSuperclass().getDeclaredFields());
        }
        return Stream.empty();
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

    private Field extractFieldFromObject(Object clazz, String name) {
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
