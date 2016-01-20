package pl.morecraft.dev.settler.domain;

import javax.persistence.*;
import java.time.LocalDate;
//stałe normalne encje
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

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private String value;

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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
