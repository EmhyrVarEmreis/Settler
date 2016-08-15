package pl.morecraft.dev.settler.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@Transactional
public class ImportService {

    public void importTransactions(MultipartFile file) throws IOException {

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String content = IOUtils.toString(stream, "UTF-8");

        System.out.println(file.getContentType());

    }

}
