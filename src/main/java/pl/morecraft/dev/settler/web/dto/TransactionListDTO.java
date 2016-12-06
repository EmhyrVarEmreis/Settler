package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;
import pl.morecraft.dev.settler.web.utils.JsonDoubleCurrencySerializer;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateSerializer;

import java.util.List;

public class TransactionListDTO {

    private Long id;
    private String reference;
    private String creator;
    private List<RedistributionTinyDTO> owners;
    private List<RedistributionTinyDTO> contractors;

    @JsonSerialize(using = JsonDoubleCurrencySerializer.class)
    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double value;

    private String description;

    @JsonSerialize(using = JsonJodaLocalDateSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateDeserializer.class)
    private LocalDate evaluated;

    private Integer comments;

    private String categories;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<RedistributionTinyDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<RedistributionTinyDTO> owners) {
        this.owners = owners;
    }

    public List<RedistributionTinyDTO> getContractors() {
        return contractors;
    }

    public void setContractors(List<RedistributionTinyDTO> contractors) {
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
