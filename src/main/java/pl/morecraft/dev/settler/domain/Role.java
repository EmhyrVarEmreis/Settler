package pl.morecraft.dev.settler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "prv_role")
public class Role extends PrivilegeObject {

    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @NotAudited
    private List<RoleAssignment> roleAssignments;

    public Role() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoleAssignment> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(List<RoleAssignment> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }

}
