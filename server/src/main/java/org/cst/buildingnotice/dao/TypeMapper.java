package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Type;
import org.cst.buildingnotice.entity.TypeWithBLOBs;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TypeWithBLOBs record);

    int insertSelective(TypeWithBLOBs record);

    TypeWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TypeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TypeWithBLOBs record);

    int updateByPrimaryKey(Type record);
}