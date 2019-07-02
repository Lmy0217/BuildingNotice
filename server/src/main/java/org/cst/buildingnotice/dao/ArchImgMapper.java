package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.ArchImgKey;

public interface ArchImgMapper {
    int deleteByPrimaryKey(ArchImgKey key);

    int insert(ArchImgKey record);

    int insertSelective(ArchImgKey record);
}