package pl.morecraft.dev.settler.domain;

import org.hibernate.annotations.Where;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mod_transaction")
public class Transaction extends PrivilegeObject {

    @Column(unique = true, nullable = false)
    private String reference;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator")
    private User creator;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created = new LocalDateTime();
    private LocalDateTime evaluated;

    private Double value;

    @Column(length = 128)
    private String description;

    @OneToMany(mappedBy = "id.parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "type='O'")
    private List<Redistribution> owners;

    @OneToMany(mappedBy = "id.parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "type='C'")
    private List<Redistribution> contractors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mod_comment",
            joinColumns = {@JoinColumn(name = "prv_object", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")})
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mod_category_object",
            joinColumns = {@JoinColumn(name = "object_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private List<Category> categories;

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(LocalDateTime evaluated) {
        this.evaluated = evaluated;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<Redistribution> getOwners() {
        return owners;
    }

    public void setOwners(List<Redistribution> owners) {
        this.owners = owners;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Redistribution> getContractors() {
        return contractors;
    }

    public void setContractors(List<Redistribution> contractors) {
        this.contractors = contractors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                ", type=" + type +
                ", creator=" + creator +
                ", created=" + created +
                ", evaluated=" + evaluated +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", owners=" + owners +
                ", contractors=" + contractors +
                ", comments=" + comments +
                ", categories=" + categories +
                '}';
    }

}

