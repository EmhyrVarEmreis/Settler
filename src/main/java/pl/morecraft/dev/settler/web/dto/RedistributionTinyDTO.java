package pl.morecraft.dev.settler.web.dto;

public class RedistributionTinyDTO {

    private String user;
    private Double value;

    public RedistributionTinyDTO() {

    }

    public RedistributionTinyDTO(String user, Double value) {
        this.user = user;
        this.value = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
