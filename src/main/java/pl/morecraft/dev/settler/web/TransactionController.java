package pl.morecraft.dev.settler.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.service.TransactionService;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.dto.TransactionListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Inject
    private TransactionService transactionService;

    @RequestMapping(
            value = "/details",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TransactionDTO> get(@RequestParam(value = "id", required = true) Long userId) {
        return transactionService.get(userId);
    }

    @RequestMapping(
            value = "/details",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> save(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.save(transactionDTO);
    }

    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ListPage<TransactionListDTO> getTransactions(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                        @RequestParam(value = "sortBy", required = false, defaultValue = "-reference") String sort,
                                                        @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return transactionService.get(page, limit, sort, filters);
    }

}
