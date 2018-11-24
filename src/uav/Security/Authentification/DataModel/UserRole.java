package uav.Security.Authentification.DataModel;

import uav.Security.Authentification.UserInfo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "ROLENAME", unique = true, nullable = false)
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserInfo> users;
    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegeId", referencedColumnName = "id"))
    private Collection<UserPrivilege> privileges;

    protected UserRole() {}
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName() {
        return this.roleName;
    }
    public void setUsers(Collection<UserInfo> users) {
        this.users = users;
    }
    public Collection<UserInfo> getUsers() {
        return this.users;
    }
    public void setPrivileges(Collection<UserPrivilege> privileges) {
        this.privileges = privileges;
    }
    public Collection<UserPrivilege> getPrivileges() {
        return this.privileges;
    }
}
