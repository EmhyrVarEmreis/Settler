package pl.morecraft.dev.settler.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.service.dictionary.DictionaryService;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DictionaryController {

    @Inject
    DictionaryService dictionaryService;

    @RequestMapping("/dictionary")
    public List<?> getDict(@RequestParam("name") String name) {
        return dictionaryService.loadDictionary(name);
    }

}
