package pl.morecraft.dev.settler.domain;

import org.hibernate.envers.NotAudited;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contractor")
    private User contractor;

    private Double value;

    private LocalDate created;
    private LocalDate confirmed;
    private LocalDate evaluated;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getContractor() {
        return contractor;
    }

    public void setContractor(User contractor) {
        this.contractor = contractor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }

    public LocalDate getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(LocalDate evaluated) {
        this.evaluated = evaluated;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

