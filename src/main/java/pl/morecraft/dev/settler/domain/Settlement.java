package pl.morecraft.dev.settler.domain;

import org.hibernate.envers.NotAudited;
import pl.morecraft.dev.settler.domain.dictionaries.SettlementType;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contractor")
    private User contractor;

    private Double balance;

    private LocalDate created;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate evaluated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "prv_object_hierarchy",
            joinColumns = {@JoinColumn(name = "prv_object_from", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "prv_object_to", referencedColumnName = "id")})
    @NotAudited
    private List<Transaction> transactions;

    @OneToMany(fetch = FetchType.LAZY)
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(LocalDate evaluated) {
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
