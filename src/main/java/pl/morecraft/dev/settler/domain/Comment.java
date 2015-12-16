package pl.morecraft.dev.settler.domain;

import java.time.LocalDate;

public class Comment extends PrivilegeObject {

    private User owner;
    private LocalDate created;
    private String content;

    public Comment() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
