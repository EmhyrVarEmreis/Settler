package pl.morecraft.dev.settler.domain;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.WhereJoinTable;
import org.hibernate.envers.NotAudited;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mod_transaction")
//@Audited
public class Transaction extends PrivilegeObject {

    @Column(unique = true, nullable = false)
    private String reference;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany
    @NotAudited
    @JoinTable(name = "mod_redistribution",
            joinColumns = {@JoinColumn(name = "parent", referencedColumnName = "id")})
    @WhereJoinTable(clause = "type='O'")
    private List<Redistribution> owners;

    @OneToMany
    @NotAudited
    @JoinTable(name = "mod_redistribution",
            joinColumns = {@JoinColumn(name = "parent", referencedColumnName = "id")})
    @WhereJoinTable(clause = "type='C'")
    private List<Redistribution> contractors;

    private Double value;

    @SuppressWarnings("SpellCheckingInspection")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime created;

    @SuppressWarnings("SpellCheckingInspection")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime confirmed;

    @SuppressWarnings("SpellCheckingInspection")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime evaluated;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mod_comment",
            joinColumns = {@JoinColumn(name = "prv_object", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")})
    @NotAudited
    private List<Comment> comments;

    public Transaction() {

    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
    }

    public LocalDateTime getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(LocalDateTime evaluated) {
        this.evaluated = evaluated;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

