package org.cst.buildingnotice.dao;

import java.util.List;

import org.cst.buildingnotice.entity.ArchImg;

public interface ArchImgMapper {
    int insert(ArchImg record);

    int insertSelective(ArchImg record);
    
    List<Integer> selectByArchid(int archid);
}