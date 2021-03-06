package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.service.CategoryService;
import pl.morecraft.dev.settler.web.dto.CategoryDTO;
import pl.morecraft.dev.settler.web.dto.CategoryListDTO;
import pl.morecraft.dev.settler.web.dto.CategoryWithValueDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(
            value = "/details",
            method = RequestMethod.GET
    )
    public ResponseEntity<CategoryDTO> get(@RequestParam(value = "id") Long userId) {
        return categoryService.get(userId);
    }

    @RequestMapping(
            value = "/details",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO dto) {
        return categoryService.save(dto);
    }

    @RequestMapping("/list")
    public ListPage<CategoryListDTO> getPaged(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(value = "limit", required = false, defaultValue = "25") Integer limit,
                                              @RequestParam(value = "sortBy", required = false, defaultValue = "-id") String sortBy,
                                              @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return categoryService.get(page, limit, sortBy, filters);
    }

    @RequestMapping("/search/simple")
    public ResponseEntity<List<CategoryListDTO>> getPaged(@RequestParam(value = "limit", required = false, defaultValue = "25") Integer limit,
                                                          @RequestParam(value = "string", required = false, defaultValue = "") String string) {
        return categoryService.searchSimple(limit, string);
    }

    @RequestMapping(
            value = "/values",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<CategoryWithValueDTO>> getCategoriesWithValue(@RequestParam(value = "count", required = false, defaultValue = "-13") Long userId) {
        return categoryService.getCategoriesWithValue(userId);
    }

}
