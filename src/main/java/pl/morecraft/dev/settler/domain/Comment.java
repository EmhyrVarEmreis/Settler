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
    private PrivilegeObject parentComment;

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

    public Comment(PrivilegeObject object, PrivilegeObject parentComment, User owner, LocalDateTime created, LocalDateTime edited, String value) {
        this.object = object;
        this.parentComment = parentComment;
        this.owner = owner;
        this.created = created;
        this.edited = edited;
        this.value = value;
    }

    public PrivilegeObject getObject() {
        return object;
    }

    public void setObject(PrivilegeObject object) {
        this.object = object;
    }

    public PrivilegeObject getParentComment() {
        return parentComment;
    }

    public void setParentComment(PrivilegeObject parentComment) {
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
