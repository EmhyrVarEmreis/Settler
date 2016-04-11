package pl.morecraft.dev.settler.domain;

import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "mod_comment")
public class Comment extends PrivilegeObject {

    @ManyToOne
    @JoinColumn(name = "prv_object")
    private PrivilegeObject object;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "prv_user")
    private User owner;

    @Column(nullable = false, insertable = true, updatable = false)
    private LocalDateTime created;

    @Column(nullable = true)
    private LocalDateTime edited;

    @Column(nullable = false)
    private String value;

    public Comment() {

    }

    public PrivilegeObject getObject() {
        return object;
    }

    public void setObject(PrivilegeObject object) {
        this.object = object;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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
