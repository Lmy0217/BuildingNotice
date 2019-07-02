package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Type2;

public interface Type2Mapper {
    int deleteByPrimaryKey(Integer archid);

    int insert(Type2 record);

    int insertSelective(Type2 record);

    Type2 selectByPrimaryKey(Integer archid);

    int updateByPrimaryKeySelective(Type2 record);

    int updateByPrimaryKey(Type2 record);
}