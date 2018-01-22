package pl.morecraft.dev.settler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
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

