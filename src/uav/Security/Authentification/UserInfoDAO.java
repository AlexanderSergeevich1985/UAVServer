package uav.Security.Authentification;

import java.util.List;

/**
 * Created by administrator on 30.05.18.
 */

public interface UserInfoDAO {
    public void addContact(UserInfo contact);
    public List<UserInfo> listContact();
    public void removeContact(Integer id);
    public UserInfo retriveUserInfo(Integer id);
    public List<UserInfo> findUsersByTelephone(String telephone);
}
