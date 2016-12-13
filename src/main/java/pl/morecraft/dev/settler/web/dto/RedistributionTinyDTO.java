package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;

public class RedistributionTinyDTO {

    private String user;

    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double percentage;

    public RedistributionTinyDTO() {

    }

    public RedistributionTinyDTO(String user, Double percentage) {
        this.user = user;
        this.percentage = percentage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

}
