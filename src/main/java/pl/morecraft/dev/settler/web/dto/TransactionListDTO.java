package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocaldateTimeDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocaldateTimeSerializer;

public class TransactionListDTO {

    private Long id;
    private String reference;
    private String type;
    private String creator;
    private String owners;
    private String contractors;
    private Double value;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime created;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime confirmed;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime evaluated;

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

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getContractors() {
        return contractors;
    }

    public void setContractors(String contractors) {
        this.contractors = contractors;
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

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
