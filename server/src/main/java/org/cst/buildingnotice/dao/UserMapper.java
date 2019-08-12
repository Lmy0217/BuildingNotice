package org.cst.buildingnotice.dao;

import java.util.List;

import org.cst.buildingnotice.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> selectByName(String name);
    
    List<User> selectByEmail(String email);
    
    List<User> selectByRole(Integer role);
}