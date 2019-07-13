package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Archive;
import org.cst.buildingnotice.entity.ArchiveWithBLOBs;

public interface ArchiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArchiveWithBLOBs record);

    int insertSelective(ArchiveWithBLOBs record);

    ArchiveWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArchiveWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArchiveWithBLOBs record);

    int updateByPrimaryKey(Archive record);
}