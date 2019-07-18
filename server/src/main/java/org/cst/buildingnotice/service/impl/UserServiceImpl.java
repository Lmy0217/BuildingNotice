package org.cst.buildingnotice.service.impl;

import java.util.List;

import org.cst.buildingnotice.dao.UserMapper;
import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserMapper userMapper;

	public Integer create(String name, String pwd, String salt) {
		User user = new User();
		user.setName(name);
		user.setPwd(pwd);
		user.setSalt(salt);
		userMapper.insertSelective(user);
		return user.getId();
	}

	public User getUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public List<User> getUserByName(String name) {
		return userMapper.selectByName(name);
	}

	public int updateById(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	public List<User> getUsersByRole(Integer role) {
		return userMapper.selectByRole(role);
	}
}
