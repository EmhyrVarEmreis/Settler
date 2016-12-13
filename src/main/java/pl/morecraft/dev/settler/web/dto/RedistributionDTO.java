package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class RedistributionDTO {

    @Enumerated(EnumType.STRING)
    private String type;

    private Long parent;

    private UserTinyDTO user;

    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double percentage;

    public RedistributionDTO() {

    }

    public RedistributionDTO(String type, Long parent, UserTinyDTO user, Double percentage) {
        this.type = type;
        this.parent = parent;
        this.user = user;
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public UserTinyDTO getUser() {
        return user;
    }

    public void setUser(UserTinyDTO user) {
        this.user = user;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

}
