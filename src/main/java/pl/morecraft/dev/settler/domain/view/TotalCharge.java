package pl.morecraft.dev.settler.domain.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_total_charge")
public class TotalCharge {

    @Id
    private Long id;

    private String userFrom;
    private Long userFromId;
    private String userTo;
    private Long userToId;

    private Double charge;

    public TotalCharge() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public Long getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Long userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public Long getUserToId() {
        return userToId;
    }

    public void setUserToId(Long userToId) {
        this.userToId = userToId;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "TotalCharge{" +
                "id=" + id +
                ", userFrom='" + userFrom + '\'' +
                ", userFromId=" + userFromId +
                ", userTo='" + userTo + '\'' +
                ", userToId=" + userToId +
                ", charge=" + charge +
                '}';
    }

}
