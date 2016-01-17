package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.morecraft.dev.settler.web.utils.JsonDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDateSerializer;

import java.time.LocalDate;

public class TransactionListDTO {

    private Long id;
    private String reference;
    private String type;
    private String owner;
    private String contractor;
    private Double value;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate created;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate confirmed;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate evaluated;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
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

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
