package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.service.HomeService;
import pl.morecraft.dev.settler.web.dto.HomeDTO;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public ResponseEntity<HomeDTO> get() {
        return homeService.getHome();
    }

}
