package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Type4;

public interface Type4Mapper {
    int deleteByPrimaryKey(Integer archid);

    int insert(Type4 record);

    int insertSelective(Type4 record);

    Type4 selectByPrimaryKey(Integer archid);

    int updateByPrimaryKeySelective(Type4 record);

    int updateByPrimaryKey(Type4 record);
}