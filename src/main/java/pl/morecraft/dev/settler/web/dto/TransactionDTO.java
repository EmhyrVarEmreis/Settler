package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDoubleSerializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateTimeDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class TransactionDTO {

    @NotNull
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotNull
    @Size(min = 1, max = 32)
    private String reference;

    @Pattern(regexp = "^[A-Z]*$")
    @NotNull
    @Size(min = 3, max = 3)
    private String type;

    @NotNull
    private UserTinyDTO creator;

    @NotNull
    private List<RedistributionPDTO> owners;

    @NotNull
    private List<RedistributionPDTO> contractors;

    @NotNull
    @JsonSerialize(using = JsonDoubleSerializer.class)
    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double value;

    @NotNull
    @JsonSerialize(using = JsonJodaLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @JsonSerialize(using = JsonJodaLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateTimeDeserializer.class)
    private LocalDateTime confirmed;

    @JsonSerialize(using = JsonJodaLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateTimeDeserializer.class)
    private LocalDateTime evaluated;

    @NotNull
    private Integer comments;

    public TransactionDTO() {
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

    public UserTinyDTO getCreator() {
        return creator;
    }

    public void setCreator(UserTinyDTO creator) {
        this.creator = creator;
    }

    public List<RedistributionPDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<RedistributionPDTO> owners) {
        this.owners = owners;
    }

    public List<RedistributionPDTO> getContractors() {
        return contractors;
    }

    public void setContractors(List<RedistributionPDTO> contractors) {
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
