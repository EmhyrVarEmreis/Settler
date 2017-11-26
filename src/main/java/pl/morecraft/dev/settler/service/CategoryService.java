package pl.morecraft.dev.settler.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.CategoryRepository;
import pl.morecraft.dev.settler.domain.Category;
import pl.morecraft.dev.settler.domain.QCategory;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.CategoryDTO;
import pl.morecraft.dev.settler.web.dto.CategoryListDTO;
import pl.morecraft.dev.settler.web.dto.CategoryWithValueDTO;
import pl.morecraft.dev.settler.web.misc.CategoryListFilters;
import pl.morecraft.dev.settler.web.misc.ListPage;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CategoryService extends AbstractService<Category, CategoryDTO, CategoryListDTO, CategoryListFilters, QCategory, Long, CategoryRepository> {

    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(EntityManager entityManager, CategoryRepository categoryRepository) {
        this.entityManager = entityManager;
        this.categoryRepository = categoryRepository;
    }

    protected CategoryRepository getUserRepository() {
        return categoryRepository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return Boolean.TRUE;
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    protected Class<CategoryDTO> getDtoClass() {
        return CategoryDTO.class;
    }

    @Override
    protected Class<CategoryListDTO> getListDtoClass() {
        return CategoryListDTO.class;
    }

    @Override
    protected Class<CategoryListFilters> getListFilterClass() {
        return CategoryListFilters.class;
    }

    @Override
    protected QCategory getEQ() {
        return QCategory.category;
    }

    public ResponseEntity<List<CategoryListDTO>> searchSimple(Integer limit, String string) {
        String[] tab = string.split("\\s+");
        ListPage<CategoryListDTO> listPage = get(
                1,
                limit,
                "-code",
                "{\"code\":\"" + tab[0] + "\",\"description\":\"" + (tab.length == 1 ? tab[0] : tab[1]) + "\"}",
                tab.length != 1
        );
        return new ResponseEntity<>(listPage.getContent(), HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryWithValueDTO>> getCategoriesWithValue(Long userId) {
        getPermissionManager().authorizeGlobalAdmin();

        if (Objects.isNull(userId) || userId < 0) {
            userId = Security.currentUser().getId();
        }
        QCategory category = QCategory.category;
        QTransaction transaction = QTransaction.transaction;
        List<Tuple> fetch = new JPAQuery<>(entityManager)
                .from(category, transaction)
                .select(category, transaction.value.sum())
                .where(transaction.creator.id.eq(userId))
                .where(transaction.categories.contains(category))
                .groupBy(category.code, category.description, category.id)
                .orderBy(transaction.value.sum().desc())
                .fetch();
        ModelMapper preparedModelMapper = getModelMapper();
        List<CategoryWithValueDTO> categoryWithValueDTOList = new ArrayList<>(fetch.size());
        for (Tuple tuple : fetch) {
            Category c = tuple.get(category);
            Double d = tuple.get(transaction.value.sum());
            if (Objects.nonNull(c)) {
                categoryWithValueDTOList.add(
                        new CategoryWithValueDTO(
                                userId,
                                preparedModelMapper.map(c, CategoryDTO.class),
                                d
                        )
                );
            }
        }
        return new ResponseEntity<>(categoryWithValueDTOList, HttpStatus.OK);
    }

}
