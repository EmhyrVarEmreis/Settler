package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.service.TransactionEntryService;
import pl.morecraft.dev.settler.web.dto.TransactionEntryListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;

@RestController
@RequestMapping("/api/transaction/view")
public class TransactionViewController {

    private final TransactionEntryService transactionEntryService;

    @Autowired
    public TransactionViewController(TransactionEntryService transactionEntryService) {
        this.transactionEntryService = transactionEntryService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public ListPage<TransactionEntryListDTO> getPaged(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                      @RequestParam(value = "sortBy", required = false, defaultValue = "-reference") String sort,
                                                      @RequestParam(value = "filters", required = false, defaultValue = "") String filters) {
        return transactionEntryService.get(page, limit, sort, filters);
    }

}
