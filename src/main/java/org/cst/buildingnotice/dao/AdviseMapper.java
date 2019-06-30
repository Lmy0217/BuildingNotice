package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Advise;

public interface AdviseMapper {
    int deleteByPrimaryKey(String name);

    int insert(Advise record);

    int insertSelective(Advise record);

    Advise selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Advise record);

    int updateByPrimaryKeyWithBLOBs(Advise record);
}