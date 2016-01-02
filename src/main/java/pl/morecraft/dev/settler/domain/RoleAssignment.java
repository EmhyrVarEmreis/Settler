package pl.morecraft.dev.settler.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "prv_user_role")
@IdClass(RoleAssignment.RoleAssignmentPK.class)
public class RoleAssignment {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "usr_user")
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "prv_role")
    private Role role;

    public RoleAssignment() {
    }

    public RoleAssignment(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleAssignment that = (RoleAssignment) o;

        if (!user.equals(that.user)) return false;
        if (!role.equals(that.role)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 41 * result + role.hashCode();
        return result;
    }

    public static class RoleAssignmentPK implements Serializable {

        private Long user;
        private Long role;

        public RoleAssignmentPK() {
        }

        public RoleAssignmentPK(User user, Role role) {
            this.user = user.getId();
            this.role = role.getId();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RoleAssignmentPK that = (RoleAssignmentPK) o;

            if (!user.equals(that.user)) return false;
            if (!role.equals(that.role)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = user.hashCode();
            result = 41 * result + role.hashCode();
            return result;
        }
    }
}
