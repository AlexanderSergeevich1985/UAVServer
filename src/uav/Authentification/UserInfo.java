package uav.Authentification;
/**
 * Created by administrator on 22.06.17.
 */

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@NamedQueries({
        @NamedQuery( name = UserInfo.NamedQuery.USER_FIND_ALL, query = "from UserInfo" ),
        @NamedQuery( name = UserInfo.NamedQuery.USER_FIND_BY_ID, query = "from UserInfo where id = :id")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = UserInfo.NamedQuery.USER_FIND_BY_Phone,
                query = "select * from USERINFOS where telephone like :telephone",
                resultClass = UserInfo.class
        )
})

@Entity
@Table(name = "USERINFOS")
public class UserInfo {
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
    public Date getBirthday() {
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        Date birth_date = null;
        try {
            birth_date = sdfr.parse(birthday);
        }
        catch(Exception ex) {
            System.out.println(ex);
            return null;
        }
        return birth_date;
    }
    public boolean setBirthday(Date birthday_) {
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            birthday = sdfr.format(birthday_);
        }
        catch(Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public static class NamedQuery {
        public static final String USER_FIND_ALL = "UserInfo.findAll";
        public static final String USER_FIND_BY_ID = "UserInfo.findById";
        public static final String USER_FIND_BY_Phone = "UserInfo.findByTelephone";
    }

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
    @Column(name = "BIRTHDAY")
    private String birthday;
    @Column(nullable = false)
    private String password;
}
