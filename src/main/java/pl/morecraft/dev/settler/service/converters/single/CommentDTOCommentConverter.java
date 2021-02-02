package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.Comment;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;
import pl.morecraft.dev.settler.web.dto.CommentDTO;

@Component
@EntityConverter
public class CommentDTOCommentConverter extends AbstractConverter<CommentDTO, Comment> {

    private final UserRepository userRepository;

    @Autowired
    public CommentDTOCommentConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Comment convert(CommentDTO commentDTO) {
        return new Comment(
                PrivilegeObject.from(commentDTO.getObject()),
                commentDTO.getParentComment() == null ? null : PrivilegeObject.from(commentDTO.getParentComment()),
                null,
                commentDTO.getOwner() == null ? null : userRepository.findById(commentDTO.getOwner().getId()).orElse(null),
                null,
                null,
                commentDTO.getValue()
        );
    }

}
