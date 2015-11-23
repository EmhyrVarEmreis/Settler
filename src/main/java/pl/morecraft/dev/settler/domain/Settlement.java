package pl.morecraft.dev.settler.domain;

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
    private SettlementType settlementType;

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

    @ElementCollection
    @CollectionTable(name = "mod_settlement_transaction", joinColumns = @JoinColumn(name = "transaction_id"))
    @OrderColumn
    private List<Transaction> transactions;

    public Settlement() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public SettlementType getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
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
}
