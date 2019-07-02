package org.cst.buildingnotice.service.impl;

import org.cst.buildingnotice.dao.UserMapper;
import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserMapper userMapper;

	public User getUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

}
