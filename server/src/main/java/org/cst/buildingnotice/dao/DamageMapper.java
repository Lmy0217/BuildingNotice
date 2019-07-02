package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Damage;

public interface DamageMapper {
    int deleteByPrimaryKey(Integer archid);

    int insert(Damage record);

    int insertSelective(Damage record);

    Damage selectByPrimaryKey(Integer archid);

    int updateByPrimaryKeySelective(Damage record);

    int updateByPrimaryKey(Damage record);
}