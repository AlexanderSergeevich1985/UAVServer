package uav.Authentification.DataModel;

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
}
