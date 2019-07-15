package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.User;

public interface UserService {

	public Integer create(String name, String pwd, String salt);
	
	public User getUserById(int id);
	
	public List<User> getUserByName(String name);
	
	int updateById(User user);
}
