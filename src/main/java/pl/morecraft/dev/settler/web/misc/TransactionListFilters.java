package pl.morecraft.dev.settler.web.misc;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.BaseFilter;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.ExtendedJodaDateAndTimeSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.standard.*;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateSerializer;

public class TransactionListFilters {

    @BaseFilter(StringNumberPathSingleFilter.class)
    private Long id;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String reference;

    @BaseFilter(StringUserSingleFilter.class)
    private String creator;

    @BaseFilter(StringListPathRedistributionSingleFilter.class)
    private String owners;

    @BaseFilter(StringListPathRedistributionSingleFilter.class)
    private String contractors;

    @BaseFilter(StringNumberPathSingleFilter.class)
    private String value;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String description;

    @JsonSerialize(using = JsonJodaLocalDateSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateDeserializer.class)
    @BaseFilter(ExtendedJodaDateAndTimeSingleFilter.class)
    private LocalDate evaluated;

    @BaseFilter(StringListPathSizeSingleFilter.class)
    private String comments;

    @BaseFilter(StringListPathCategorySingleFilter.class)
    private String categories;

    public TransactionListFilters() {

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
