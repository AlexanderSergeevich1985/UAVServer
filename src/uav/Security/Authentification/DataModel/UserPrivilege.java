package uav.Security.Authentification.DataModel;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class UserPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "PRIVILEGENAME", unique = true, nullable = false)
    private String privilegeName;
    @ManyToMany(mappedBy = "privileges")
    private Collection<UserRole> roles;

    protected UserPrivilege() {}
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }
    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }
    public String getPrivilegeName() {
        return this.privilegeName;
    }
    public void setRoles(Collection<UserRole> roles) {
        this.roles = roles;
    }
    public Collection<UserRole> getRoles() {
        return this.roles;
    }
}
