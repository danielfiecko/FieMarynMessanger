package com.danielfiecko.fiemarynapp.dao;

import com.danielfiecko.fiemarynapp.model.User;

import java.util.List;

public interface UserDao {

	User findById(int id);

	void saveUser(User user);
	
	void deleteUserByNickname(String nickname);
	
	List<User> findAllUsers();

	List<User> findAllContacts(String loggedUserNickname);

	User findUserByNickname(String nickname);
}
