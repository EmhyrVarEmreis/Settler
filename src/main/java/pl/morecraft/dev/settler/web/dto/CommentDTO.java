package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateTimeDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateTimeSerializer;

public class CommentDTO {

    private Long object;
    private Long parentComment;
    private UserTinyDTO owner;

    @JsonSerialize(using = JsonJodaLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @JsonSerialize(using = JsonJodaLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateTimeDeserializer.class)
    private LocalDateTime edited;

    private String value;

    public CommentDTO() {

    }

    public Long getObject() {
        return object;
    }

    public void setObject(Long object) {
        this.object = object;
    }

    public Long getParentComment() {
        return parentComment;
    }

    public void setParentComment(Long parentComment) {
        this.parentComment = parentComment;
    }

    public UserTinyDTO getOwner() {
        return owner;
    }

    public void setOwner(UserTinyDTO owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
