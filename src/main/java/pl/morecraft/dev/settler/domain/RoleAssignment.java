package pl.morecraft.dev.settler.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "prv_user_role")
@IdClass(RoleAssignment.RoleAssignmentPK.class)
public class RoleAssignment {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "prv_user")
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "prv_role")
    private Role role;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "target")
    private PrivilegeObject target;

    public RoleAssignment() {

    }

    public RoleAssignment(User user, Role role) {
        this(user, role, null);
    }

    public RoleAssignment(User user, Role role, PrivilegeObject target) {
        this.user = user;
        this.role = role;
        this.target = target;
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

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleAssignment that = (RoleAssignment) o;

        if (!user.equals(that.user)) return false;
        if (!role.equals(that.role)) return false;
        return target != null ? target.equals(that.target) : that.target == null;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    public static class RoleAssignmentPK implements Serializable {

        private Long user;
        private Long role;
        private Long target;

        public RoleAssignmentPK() {

        }

        public RoleAssignmentPK(Long user, Long role, Long target) {
            this.user = user;
            this.role = role;
            this.target = target;
        }

        @SuppressWarnings("SimplifiableIfStatement")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RoleAssignmentPK that = (RoleAssignmentPK) o;

            if (!user.equals(that.user)) return false;
            if (!role.equals(that.role)) return false;
            return target != null ? target.equals(that.target) : that.target == null;
        }

        @Override
        public int hashCode() {
            int result = user.hashCode();
            result = 31 * result + role.hashCode();
            result = 31 * result + (target != null ? target.hashCode() : 0);
            return result;
        }

    }

}
