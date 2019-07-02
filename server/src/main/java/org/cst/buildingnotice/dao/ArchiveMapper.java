package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Archive;

public interface ArchiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Archive record);

    int insertSelective(Archive record);

    Archive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Archive record);

    int updateByPrimaryKey(Archive record);
}