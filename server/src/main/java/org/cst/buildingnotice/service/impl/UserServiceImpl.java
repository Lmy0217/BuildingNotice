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

	public int create(String name, String pwd) {
		User user = new User();
		user.setName(name);
		user.setPwd(pwd);
		userMapper.insertSelective(user);
		return user.getId();
	}

	public User getUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
