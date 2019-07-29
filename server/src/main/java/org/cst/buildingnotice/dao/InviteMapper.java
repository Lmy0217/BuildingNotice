package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Invite;

public interface InviteMapper {
    int deleteByPrimaryKey(String code);

    int insert(Invite record);

    int insertSelective(Invite record);

    Invite selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Invite record);

    int updateByPrimaryKey(Invite record);
}