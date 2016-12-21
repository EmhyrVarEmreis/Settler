package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.morecraft.dev.settler.service.FileService;
import pl.morecraft.dev.settler.service.UserService;
import pl.morecraft.dev.settler.web.dto.FileObjectDTO;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @RequestMapping(
            value = "/upload",
            method = {RequestMethod.POST, RequestMethod.PUT},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FileObjectDTO> upload(
            @RequestParam(value = "id", defaultValue = "-13", required = false) Long id,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "login", defaultValue = "", required = false) String login,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        FileObjectDTO fileObjectDTO = new FileObjectDTO();

        if (name == null || name.equalsIgnoreCase("null")) {
            name = null;
        }

        if (login == null || login.equalsIgnoreCase("null")) {
            login = null;
        }

        if (id > 0) {
            fileObjectDTO.setId(id);
        } else if (name != null) {
            fileObjectDTO.setName(name);
        }

        fileObjectDTO.setType(file.getContentType());
        fileObjectDTO.setContent(file.getBytes());
        fileObjectDTO.setPath(file.getOriginalFilename());

        if (login == null) {
            return fileService.save(fileObjectDTO);
        } else {
            return userService.setAvatar(login, fileObjectDTO);
        }
    }

}
