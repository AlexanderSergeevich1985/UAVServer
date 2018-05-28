package uav.Authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by administrator on 23.06.17.
 */
interface ContactService {
    public void addContact(UserInfo contact);
    public List<UserInfo> listContact();
    public void removeContact(Integer id);
}

public class UserInfoServiceImpl {
    @Autowired
    private UserInfoDAO contactDAO;
    @Transactional
    public void addContact(UserInfo contact) {
        contactDAO.addContact(contact);
    }
    @Transactional
    public List<UserInfo> listContact() {

        return contactDAO.listContact();
    }
    @Transactional
    public void removeContact(Integer id) {
        contactDAO.removeContact(id);
    }
}
