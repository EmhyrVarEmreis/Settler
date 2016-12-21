package pl.morecraft.dev.settler.service;

import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.CommentRepository;
import pl.morecraft.dev.settler.dao.repository.PrivilegeObjectRepository;
import pl.morecraft.dev.settler.domain.Comment;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.QComment;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.CommentDTO;
import pl.morecraft.dev.settler.web.dto.CommentListDTO;
import pl.morecraft.dev.settler.web.misc.CommentListFilters;

import javax.inject.Inject;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@Transactional
public class CommentService extends AbstractService<Comment, CommentDTO, CommentListDTO, CommentListFilters, QComment, Long, CommentRepository> {

    private final CommentRepository commentRepository;
    private final PrivilegeObjectRepository privilegeObjectRepository;

    @Inject
    public CommentService(CommentRepository commentRepository, PrivilegeObjectRepository privilegeObjectRepository) {
        this.commentRepository = commentRepository;
        this.privilegeObjectRepository = privilegeObjectRepository;
    }

    protected CommentRepository getUserRepository() {
        return commentRepository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return true;
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    protected Class<CommentDTO> getDtoClass() {
        return CommentDTO.class;
    }

    @Override
    protected Class<CommentListDTO> getListDtoClass() {
        return CommentListDTO.class;
    }

    @Override
    protected Class<CommentListFilters> getListFilterClass() {
        return CommentListFilters.class;
    }

    @Override
    protected QComment getEQ() {
        return QComment.comment;
    }

    @Override
    protected UnaryOperator<Comment> getSaveSavePreProcessingFunction() {
        return comment -> {
            if (comment.getOwner() == null) {
                comment.setOwner(Security.currentUser());
            }
            if (comment.getCreated() == null) {
                comment.setCreated(new LocalDateTime());
            }
            if (comment.getId() != null) {
                comment.setEdited(new LocalDateTime());
            }
            return super.getSaveSavePreProcessingFunction().apply(comment);
        };
    }

    public ResponseEntity<List<CommentDTO>> getByPrivilegeObject(Long objectId) {
        PrivilegeObject privilegeObject = privilegeObjectRepository.findOne(objectId);

        if (privilegeObject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Comment> commentList = getByPrivilegeObject(privilegeObject);

        List<CommentDTO> commentDTOList = getListPageConverter().convert(commentList, CommentDTO.class);

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    private List<Comment> getByPrivilegeObject(PrivilegeObject privilegeObject) {
        return commentRepository.findAllByObject(privilegeObject);
    }

}
