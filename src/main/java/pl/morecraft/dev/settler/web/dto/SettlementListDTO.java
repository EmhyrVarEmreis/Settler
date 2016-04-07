package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocaldateTimeDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocaldateTimeSerializer;

public class SettlementListDTO {

    private Long id;
    private String reference;
    private String type;
    private String creator;
    private String contractor;
    private Double balance;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime created;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime startDate;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime endDate;

    @JsonDeserialize(using = JsonJodaLocaldateTimeDeserializer.class)
    @JsonSerialize(using = JsonJodaLocaldateTimeSerializer.class)
    private LocalDateTime evaluated;

    private Integer transactions;
    private Integer comments;

    public SettlementListDTO() {

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

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(LocalDateTime evaluated) {
        this.evaluated = evaluated;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

}
