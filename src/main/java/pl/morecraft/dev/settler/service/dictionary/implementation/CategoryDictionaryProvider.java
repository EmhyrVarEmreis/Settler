package pl.morecraft.dev.settler.service.dictionary.implementation;


import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.repository.CategoryRepository;
import pl.morecraft.dev.settler.service.dictionary.DictionaryProvider;
import pl.morecraft.dev.settler.service.dictionary.DictionaryProviderFor;
import pl.morecraft.dev.settler.web.dto.CategoryDctDTO;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@DictionaryProviderFor("category")
public class CategoryDictionaryProvider implements DictionaryProvider {

    @Inject
    CategoryRepository categoryRepository;

    @Override
    public List loadProvider() {
        return categoryRepository.findAll().stream().map(
                category -> new CategoryDctDTO(
                        category.getCode(),
                        category.getDescription()
                )
        ).collect(Collectors.toList());
    }

}
