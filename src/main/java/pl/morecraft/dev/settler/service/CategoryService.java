package pl.morecraft.dev.settler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.CategoryRepository;
import pl.morecraft.dev.settler.domain.Category;
import pl.morecraft.dev.settler.domain.QCategory;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.CategoryDTO;
import pl.morecraft.dev.settler.web.dto.CategoryListDTO;
import pl.morecraft.dev.settler.web.misc.CategoryListFilters;
import pl.morecraft.dev.settler.web.misc.ListPage;

import java.util.List;

@Service
@Transactional
public class CategoryService extends AbstractService<Category, CategoryDTO, CategoryListDTO, CategoryListFilters, QCategory, Long, CategoryRepository> {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    protected CategoryRepository getUserRepository() {
        return repository;
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

}
