package uav.Authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by administrator on 23.06.17.
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDAO userInfoDAO;
    @Transactional
    public void addContact(UserInfo contact) {
        userInfoDAO.addContact(contact);
    }
    @Transactional
    public List<UserInfo> listContact() {
        return userInfoDAO.listContact();
    }
    @Transactional
    public void removeContact(Integer id) {
        userInfoDAO.removeContact(id);
    }
    @Transactional
    public UserInfo retriveUserInfo(Integer id) {
        return userInfoDAO.retriveUserInfo(id);
    }
    @Transactional
    public List<UserInfo> findUsersByTelephone(String telephone) {
        return userInfoDAO.findUsersByTelephone(telephone);
    }
}
