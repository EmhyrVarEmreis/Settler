package pl.morecraft.dev.settler.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.morecraft.dev.settler.service.CommentService;
import pl.morecraft.dev.settler.web.dto.CommentDTO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Inject
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(
            value = "/getByObjectId",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<CommentDTO>> getByObjectId(
            @RequestParam(value = "id") Long objectId
    ) {
        return commentService.getByPrivilegeObject(objectId);
    }

}
