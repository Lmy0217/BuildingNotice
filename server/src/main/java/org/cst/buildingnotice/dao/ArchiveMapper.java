package org.cst.buildingnotice.dao;

import java.util.List;
import java.util.Map;

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
    
    List<ArchiveWithBLOBs> selectByUserid(Integer userid);
    
    int countByUserid(Integer userid);
    
    List<Map<String, Object>> statusCountByUserid(Integer userid);
}