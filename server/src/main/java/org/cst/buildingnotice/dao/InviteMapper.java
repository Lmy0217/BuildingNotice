package org.cst.buildingnotice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cst.buildingnotice.entity.Invite;

public interface InviteMapper {
    int deleteByPrimaryKey(String code);

    int insert(Invite record);
    
    int insertCodes(@Param("codes") List<String> codes, @Param("createid") Integer createid);

    int insertSelective(Invite record);

    Invite selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Invite record);

    int updateByPrimaryKey(Invite record);
    
    List<Invite> selectByCreateid(Integer createid);
}