package pl.morecraft.dev.settler.domain;


import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mod_redistribution")
@IdClass(Redistribution.RedistributionPK.class)
public class Redistribution {

    @Id
    @Enumerated(EnumType.STRING)
    private RedistributionType type;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "parent")
    private PrivilegeObject parent;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    private Double value;

    public Redistribution() {

    }

    public Redistribution(RedistributionType type, PrivilegeObject parent, User user, Double value) {
        this.type = type;
        this.parent = parent;
        this.user = user;
        this.value = value;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Redistribution that = (Redistribution) o;

        if (!type.equals(that.type)) {
            return false;
        }
        if (!parent.equals(that.parent)) {
            return false;
        }
        if (!user.equals(that.user)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 41 * result + parent.hashCode();
        result = 41 * result + user.hashCode();
        return result;
    }

    public static class RedistributionPK implements Serializable {

        private RedistributionType type;
        private Long parent;
        private Long user;

        public RedistributionPK() {
        }

        public RedistributionPK(RedistributionType type, PrivilegeObject parent, User user) {
            this.type = type;
            this.parent = parent.getId();
            this.user = user.getId();
        }

        @SuppressWarnings("RedundantIfStatement")
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            RedistributionPK that = (RedistributionPK) o;

            if (!type.equals(that.type)) {
                return false;
            }
            if (!parent.equals(that.parent)) {
                return false;
            }
            if (!user.equals(that.user)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 41 * result + parent.hashCode();
            result = 41 * result + user.hashCode();
            return result;
        }
    }

}
