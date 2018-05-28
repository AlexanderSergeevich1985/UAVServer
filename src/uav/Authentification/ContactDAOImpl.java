package uav.Authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by administrator on 23.06.17.
 */
interface UserInfoDAO {
    public void addContact(UserInfo contact);
    public List<UserInfo> listContact();
    public void removeContact(Integer id);
}

@Repository
public class ContactDAOImpl implements UserInfoDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public void addContact(UserInfo contact) {
        sessionFactory.getCurrentSession().save(contact);
    }
    @SuppressWarnings("unchecked")
    public List<UserInfo> listContact() {
        return sessionFactory.getCurrentSession().createQuery("from Contact").list();
    }
    public void removeContact(Integer id) {
        UserInfo contact = (UserInfo) sessionFactory.getCurrentSession().load(UserInfo.class, id);
        if(null != contact) {
            sessionFactory.getCurrentSession().delete(contact);
        }
    }
}