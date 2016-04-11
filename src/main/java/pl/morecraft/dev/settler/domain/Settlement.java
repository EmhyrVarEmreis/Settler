package pl.morecraft.dev.settler.domain;

import org.hibernate.envers.NotAudited;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.domain.dictionaries.SettlementType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mod_settlement")
//@Audited
public class Settlement extends PrivilegeObject {

    @Column(unique = true, nullable = false)
    private String reference;

    @Enumerated(EnumType.STRING)
    private SettlementType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contractor")
    private User contractor;

    private Double balance;

    @Column(nullable = false, insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime evaluated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "prv_object_hierarchy",
            joinColumns = {@JoinColumn(name = "prv_object_from", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "prv_object_to", referencedColumnName = "id")})
    @NotAudited
    private List<Transaction> transactions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "mod_comment",
            joinColumns = {@JoinColumn(name = "prv_object", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")})
    @NotAudited
    private List<Comment> comments;

    public Settlement() {

    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public SettlementType getType() {
        return type;
    }

    public void setType(SettlementType type) {
        this.type = type;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getContractor() {
        return contractor;
    }

    public void setContractor(User contractor) {
        this.contractor = contractor;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(LocalDateTime evaluated) {
        this.evaluated = evaluated;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
