package uav.Security.Authentification.DataModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by administrator on 04.06.18.
 */

@Entity
@Table(name="USER_PROFILE")
public class UserProfile implements Serializable {

    protected UserProfile() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        else if(obj == null || !(obj instanceof UserProfile))
            return false;
        UserProfile other = (UserProfile) obj;
        if(id == null || other.id == null) {
            return false;
        }
        else if(!id.equals(other.id))
            return false;
        if(type == null || other.type == null) {
            return false;
        }
        else if(!type.equals(other.type))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "UserProfile [id=" + id + ", type=" + type + "]";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="TYPE", length=10, unique=true, nullable=false)
    private String type = UserProfileType.USER.getUserProfileType();
}
