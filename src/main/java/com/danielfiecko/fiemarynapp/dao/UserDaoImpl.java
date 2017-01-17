package com.danielfiecko.fiemarynapp.dao;

import com.danielfiecko.fiemarynapp.model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    public User findById(int id) {
        return getByKey(id);
    }

    public void saveUser(User user) {
        persist(user);
    }

    public void deleteUserByNickname(String nickname) {
        Query query = getSession().createSQLQuery("delete from users where nickname = :nickname");
        query.setString("nickname", nickname);
        query.executeUpdate();
    }

    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
    }

    public List<User> findAllContacts(String loggedUserNickname) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.ne("nickname", loggedUserNickname));
        return (List<User>) criteria.list();
    }


    public User findUserByNickname(String nickname) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("nickname", nickname));
        return (User) criteria.uniqueResult();
    }
}
