package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.User;

public interface UserService {

	public int create(String name, String pwd);
	
	public User getUserById(int id);
}
