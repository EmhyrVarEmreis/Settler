package pl.morecraft.dev.settler.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.morecraft.dev.settler.web.utils.JsonDoubleDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonDoubleSerializer;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class RedistributionDTO {

    @Enumerated(EnumType.STRING)
    private String type;

    private Long parent;

    private UserTinyDTO user;

    @JsonSerialize(using = JsonDoubleSerializer.class)
    @JsonDeserialize(using = JsonDoubleDeserializer.class)
    private Double value;

    public RedistributionDTO() {

    }

    public RedistributionDTO(String type, Long parent, UserTinyDTO user, Double value) {
        this.type = type;
        this.parent = parent;
        this.user = user;
        this.value = value;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
