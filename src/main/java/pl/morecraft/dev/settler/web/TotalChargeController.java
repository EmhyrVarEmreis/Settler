package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.TotalChargeService;
import pl.morecraft.dev.settler.web.dto.TotalChargeDTO;
import pl.morecraft.dev.settler.web.dto.TotalChargeListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import java.util.List;

@RestController
@RequestMapping("/api/charge")
public class TotalChargeController {

    private final TotalChargeService totalChargeService;

    @Autowired
    public TotalChargeController(TotalChargeService totalChargeService) {
        this.totalChargeService = totalChargeService;
    }

    @RequestMapping(
            value = "/details",
            method = RequestMethod.GET
    )
    public ResponseEntity<TotalChargeDTO> get(@RequestParam(value = "id") Long id) {
        return totalChargeService.get(id);
    }

    @RequestMapping("/list")
    public ListPage<TotalChargeListDTO> getPaged(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "25") Integer limit,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "-id") String sortBy,
                                                 @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return totalChargeService.get(page, limit, sortBy, filters);
    }

    @RequestMapping(
            value = "/user/from",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<TotalChargeDTO>> getByUserFromId(
            @RequestParam(value = "id", required = false, defaultValue = "-1") Long id
    ) {
        if (id < 0) {
            id = Security.currentUser().getId();
        }
        return totalChargeService.getByUserFromId(id);
    }

    @RequestMapping(
            value = "/user/to",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<TotalChargeDTO>> getByUserToId(
            @RequestParam(value = "id", required = false, defaultValue = "-1") Long id
    ) {
        if (id < 0) {
            id = Security.currentUser().getId();
        }
        return totalChargeService.getByUserToId(id);
    }

}
