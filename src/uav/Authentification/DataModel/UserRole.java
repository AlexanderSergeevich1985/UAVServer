package uav.Authentification.DataModel;

import uav.Authentification.UserInfo;

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
}
