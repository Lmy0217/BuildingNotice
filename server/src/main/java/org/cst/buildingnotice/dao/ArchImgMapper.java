package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.ArchImg;

public interface ArchImgMapper {
    int insert(ArchImg record);

    int insertSelective(ArchImg record);
}