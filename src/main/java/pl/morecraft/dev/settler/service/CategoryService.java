package pl.morecraft.dev.settler.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.CategoryRepository;
import pl.morecraft.dev.settler.domain.Category;
import pl.morecraft.dev.settler.domain.QCategory;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.CategoryDTO;
import pl.morecraft.dev.settler.web.dto.CategoryListDTO;
import pl.morecraft.dev.settler.web.misc.CategoryListFilters;

import javax.inject.Inject;

@Service
@Transactional
public class CategoryService extends AbstractService<Category, CategoryDTO, CategoryListDTO, CategoryListFilters, QCategory, Long, CategoryRepository> {

    @Inject
    private PermissionManager permissionManager;

    @Inject
    private CategoryRepository repository;

    @Override
    protected CategoryRepository getRepository() {
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

}
