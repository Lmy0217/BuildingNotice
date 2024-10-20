package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.User;

public interface UserService {

	public Integer create(String name, String pwd, String salt, Integer role);
	
	public User getUserById(int id);
	
	public List<User> getUserByName(String name);
	
	public List<User> getUserByEmail(String email);
	
	int updateById(User user);
	
	public List<User> getUsersByRole(Integer role);
}
