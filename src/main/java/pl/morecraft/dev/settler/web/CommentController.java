package pl.morecraft.dev.settler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.morecraft.dev.settler.service.CommentService;
import pl.morecraft.dev.settler.web.dto.CommentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getByObjectId(
            @RequestParam(value = "id") Long objectId
    ) {
        return commentService.getByPrivilegeObject(objectId);
    }

    @RequestMapping(
            value = "",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
/*    public ResponseEntity<List<CommentDTO>> editCommentByObjectId(
            @RequestBody CommentDTO commentDTO
            ){

//        commentService.update(commentDTO); // TODO - kwestia do podpytania się, czy funkcja save nie robi tego automatycznie :)
    }*//*

    @RequestMapping(
            value = "",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE
    )*/
    public ResponseEntity<CommentDTO> putByObjectId(
            @RequestBody CommentDTO commentDTO
    ) {
        return commentService.save(commentDTO);
    }

    @DeleteMapping
    public ResponseEntity<List<CommentDTO>> deleteByObjectId(
            @RequestParam(value = "id") Long objectId
    ) {
        return commentService.deleteByPrivilegeObject(objectId);
    }

}
