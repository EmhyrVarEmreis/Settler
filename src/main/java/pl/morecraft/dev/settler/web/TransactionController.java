package pl.morecraft.dev.settler.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(name = "/details", method = RequestMethod.GET)
    public TransactionDTO get(@RequestParam(value = "id", required = true) Long userId) {
        return transactionService.get(userId);
    }

    @RequestMapping("/list")
    public ListPage<TransactionListDTO> getTransactions(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                        @RequestParam(value = "sortBy", required = false, defaultValue = "-reference") String sort,
                                                        @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return transactionService.get(page, limit, sort, filters);
    }

}
