package com.danielfiecko.fiemarynapp.service;

import com.danielfiecko.fiemarynapp.dao.UserDao;
import com.danielfiecko.fiemarynapp.model.User;
import org.hibernate.metamodel.relational.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public User findById(int id) {
        return dao.findById(id);
    }

    public void saveUser(User user) {
        dao.saveUser(user);
    }

    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if (entity != null) {
            entity.setFirstName(user.getFirstName());
            entity.setSecondName(user.getSecondName());
            entity.setNickname(user.getNickname());
            entity.setPassword(user.getPassword());
        }
    }

    public void deleteUserByNickname(String nickname) {
        dao.deleteUserByNickname(nickname);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public List<User> findAllContacts(String loggedUserNickname) {
        return dao.findAllContacts(loggedUserNickname);
    }

    public User findUserByNickname(String nickname) {
        return dao.findUserByNickname(nickname);
    }

    public boolean isUserNicknameUnique(Integer id, String nickname) {
        User entity = dao.findUserByNickname(nickname);
        return (entity == null || ((id != null) && (entity.getId() == id)));
    }

    public boolean isUserPasswordCorrect(String nickname, String password) {
        User entity = dao.findUserByNickname(nickname);
        boolean flag = false;

        if (isPasswordLengthCorrect(password, entity)) {
            if (isPasswordContentCorrect(password, entity)) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean nicknameExists(String nickname) {

        boolean exist = true;
        Optional<User> someUser = Optional.ofNullable(dao.findUserByNickname(nickname));
        if (!someUser.isPresent()) exist = false;
        return exist;
    }

    private boolean isPasswordLengthCorrect(String password, User entity) {
        return (password.length() > entity.minPasswordLength) && (password.length() < entity.maxPasswordLength);
    }

    private boolean isPasswordContentCorrect(String password, User entity) {
        return password.equals(entity.getPassword());
    }
}
