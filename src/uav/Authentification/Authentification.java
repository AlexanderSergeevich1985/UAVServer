package uav.Authentification;
/**
 * Created by administrator on 22.06.17.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class Authentification {
}

@Entity
@Table(name = "USERINFO")
class UserInfo {
    public Integer getId() { return id; }
    public void setId(Integer id_) { id = id_; }
    public String getFirstName() { return firstname; }
    public void setFirstName(String firstname_) { firstname = firstname_; }
    public String getLastName() { return lastname; }
    public void setLastName(String lastname_) { lastname = lastname_; }
    public String getEmail() { return email; }
    public void setEmail(String email_) { email = email_; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone_) { email = telephone_; }
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEPHONE")
    private String telephone;
}