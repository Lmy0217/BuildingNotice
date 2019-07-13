package org.cst.buildingnotice.dao;

import java.util.List;

import org.cst.buildingnotice.entity.Image;

public interface ImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
    
    List<Image> selectByIdList(List<Integer> ids);
}