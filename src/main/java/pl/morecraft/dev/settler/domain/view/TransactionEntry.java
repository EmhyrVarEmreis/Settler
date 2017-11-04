package pl.morecraft.dev.settler.domain.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDate;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "view_mod_transaction")
public class TransactionEntry {

    @Id
    private Long id;

    private String creator;
    private String owners;
    private String contractors;
    private String reference;
    private Double value;
    private String description;

    @JsonSerialize(using = JsonJodaLocalDateSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateDeserializer.class)
    private LocalDate evaluated;

    private Integer comments;
    private String categories;

}
