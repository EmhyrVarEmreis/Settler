package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.service.TransactionService;
import pl.morecraft.dev.settler.web.dto.TransactionDTO;
import pl.morecraft.dev.settler.web.dto.TransactionListDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(
            value = "/details",
            method = RequestMethod.GET
    )
    public ResponseEntity<TransactionDTO> get(@RequestParam(value = "id") Long userId) {
        return transactionService.get(userId);
    }

    @RequestMapping(
            value = "/details",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TransactionDTO> save(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.save(transactionDTO);
    }

    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET
    )
    public ListPage<TransactionListDTO> getPaged(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "-reference") String sort,
                                                 @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return transactionService.get(page, limit, sort, filters);
    }

    @RequestMapping(
            value = "/users",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<UserListDTO>> getMostUsedUsers(@RequestParam(value = "count") Long count) {
        return transactionService.getMostUsedUsers(count);
    }

}
