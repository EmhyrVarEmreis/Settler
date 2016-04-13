package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDoubleSerializer;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class RedistributionDTO {

    @Enumerated(EnumType.STRING)
    private RedistributionType type;

    private UserTinyDTO user;

    @JsonSerialize(using = JsonDoubleSerializer.class)
    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double percentage;

    public RedistributionDTO() {

    }

    public RedistributionType getType() {
        return type;
    }

    public void setType(RedistributionType type) {
        this.type = type;
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
