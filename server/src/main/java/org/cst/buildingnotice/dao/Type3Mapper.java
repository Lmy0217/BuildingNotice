package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Type3;

public interface Type3Mapper {
    int deleteByPrimaryKey(Integer archid);

    int insert(Type3 record);

    int insertSelective(Type3 record);

    Type3 selectByPrimaryKey(Integer archid);

    int updateByPrimaryKeySelective(Type3 record);

    int updateByPrimaryKey(Type3 record);
}