package pl.morecraft.dev.settler.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.service.SettlementService;
import pl.morecraft.dev.settler.web.dto.SettlementDTO;
import pl.morecraft.dev.settler.web.dto.SettlementListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/settlement")
public class SettlementController {

    @Inject
    private SettlementService settlementService;

    @RequestMapping(
            value = "/details",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SettlementDTO> get(@RequestParam(value = "id", required = true) Long userId) {
        return settlementService.get(userId);
    }

    @RequestMapping(
            value = "/details",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> save(@RequestBody SettlementDTO settlementDTO) {
        return settlementService.save(settlementDTO);
    }

    @RequestMapping("/list")
    public ListPage<SettlementListDTO> getSettlements(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                      @RequestParam(value = "sortBy", required = false, defaultValue = "-reference") String sort,
                                                      @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return settlementService.get(page, limit, sort, filters);
    }

}