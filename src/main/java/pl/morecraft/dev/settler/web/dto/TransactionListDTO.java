package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDoubleSerializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateSerializer;

import java.util.List;

public class TransactionListDTO {

    private Long id;
    private String reference;
    private String type;
    private String creator;
    private List<String> owners;
    private List<String> contractors;

    @JsonSerialize(using = JsonDoubleSerializer.class)
    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double value;

    private String description;

    @JsonSerialize(using = JsonJodaLocalDateSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateDeserializer.class)
    private LocalDate created;

    private Boolean evaluated;

    private Integer comments;

    public TransactionListDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public List<String> getContractors() {
        return contractors;
    }

    public void setContractors(List<String> contractors) {
        this.contractors = contractors;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Boolean getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        this.evaluated = evaluated;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

}
