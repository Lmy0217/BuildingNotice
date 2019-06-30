package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Type;
import org.cst.buildingnotice.entity.TypeKey;
import org.cst.buildingnotice.entity.TypeWithBLOBs;

public interface TypeMapper {
    int deleteByPrimaryKey(TypeKey key);

    int insert(TypeWithBLOBs record);

    int insertSelective(TypeWithBLOBs record);

    TypeWithBLOBs selectByPrimaryKey(TypeKey key);

    int updateByPrimaryKeySelective(TypeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TypeWithBLOBs record);

    int updateByPrimaryKey(Type record);
}