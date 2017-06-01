package pl.morecraft.dev.settler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mod_comment")
public class Comment extends PrivilegeObject {

    @ManyToOne
    @JoinColumn(name = "prv_object")
    private PrivilegeObject object;

    @ManyToOne
    @JoinColumn(name = "parent")
    private PrivilegeObject parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> childComments;

    @ManyToOne
    @JoinColumn(name = "prv_user")
    private User owner;

    @Column(nullable = false, insertable = true, updatable = false)
    private LocalDateTime created;

    @Column(nullable = true)
    private LocalDateTime edited;

    @Column(nullable = false)
    private String value;

}
