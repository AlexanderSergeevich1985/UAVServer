package uav.Security.Authentification;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by administrator on 23.06.17.
 */

@Repository
public class UserInfoDAOImpl implements UserInfoDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public void addContact(UserInfo contact) {
        try {
            sessionFactory.getCurrentSession().save(contact);
        }
        catch(HibernateException ex) {
            ex.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public List<UserInfo> listContact() {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserInfo");
            return query.list();
        }
        catch(HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void removeContact(Integer id) {
        try {
            UserInfo contact = (UserInfo) sessionFactory.getCurrentSession().load(UserInfo.class, id);
            if (null != contact) {
                sessionFactory.getCurrentSession().delete(contact);
            }
        }
        catch(HibernateException ex) {
            ex.printStackTrace();
        }
    }
    public UserInfo retriveUserInfo(Integer id) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserInfo where id = :id");
            query.setInteger("id", id);
            return (UserInfo)query.uniqueResult();
        }
        catch(HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public List<UserInfo> findUsersByTelephone(String telephone) {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(UserInfo.NamedQuery.USER_FIND_BY_Phone);
            query.setString("telephone", "%" + telephone + "%");
            return (List<UserInfo>)query.list();
        }
        catch(HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}