package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Type1;

public interface Type1Mapper {
    int deleteByPrimaryKey(Integer archid);

    int insert(Type1 record);

    int insertSelective(Type1 record);

    Type1 selectByPrimaryKey(Integer archid);

    int updateByPrimaryKeySelective(Type1 record);

    int updateByPrimaryKey(Type1 record);
}