package pl.morecraft.dev.settler.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.morecraft.dev.settler.service.ImportService;

import javax.inject.Inject;
import java.io.IOException;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    @Inject
    private ImportService importService;

    @RequestMapping(
            value = "/transactions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> importTransactions(@RequestParam("file") MultipartFile file) throws IOException {

        importService.importTransactions(file);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/settlements",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> importSettlements(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/users",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> importUsers(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
