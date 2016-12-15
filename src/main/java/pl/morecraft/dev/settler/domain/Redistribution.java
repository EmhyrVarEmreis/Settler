package pl.morecraft.dev.settler.domain;

import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mod_redistribution")
public class Redistribution {

    @EmbeddedId
    private RedistributionId id;

    private Double percentage;

    public Redistribution() {

    }

    public Redistribution(RedistributionId id, Double percentage) {
        this.id = id;
        this.percentage = percentage;
    }

    public Redistribution(RedistributionType type, PrivilegeObject parent, User user, Double percentage) {
        this.id = new RedistributionId(type, parent, user);
        this.percentage = percentage;
    }

    public RedistributionId getId() {
        return id;
    }

    public void setId(RedistributionId id) {
        this.id = id;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Redistribution that = (Redistribution) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Embeddable
    public static class RedistributionId implements Serializable {

        @Enumerated(EnumType.STRING)
        private RedistributionType type;

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "parent")
        private PrivilegeObject parent;

        @ManyToOne(optional = false)
        @JoinColumn(name = "user")
        private User user;

        public RedistributionId() {

        }

        public RedistributionId(RedistributionType type, PrivilegeObject parent, User user) {
            this.type = type;
            this.parent = parent;
            this.user = user;
        }

        public RedistributionType getType() {
            return type;
        }

        public void setType(RedistributionType type) {
            this.type = type;
        }

        public PrivilegeObject getParent() {
            return parent;
        }

        public void setParent(PrivilegeObject parent) {
            this.parent = parent;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @SuppressWarnings("SimplifiableIfStatement")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RedistributionId that = (RedistributionId) o;

            if (type != null ? !type.equals(that.type) : that.type != null) return false;
            if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
            return user != null ? user.equals(that.user) : that.user == null;
        }

        @Override
        public int hashCode() {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + (parent != null ? parent.hashCode() : 0);
            result = 31 * result + (user != null ? user.hashCode() : 0);
            return result;
        }

    }

}
