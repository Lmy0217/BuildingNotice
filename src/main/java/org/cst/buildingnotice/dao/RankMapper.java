package org.cst.buildingnotice.dao;

import org.cst.buildingnotice.entity.Rank;

public interface RankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank record);

    int insertSelective(Rank record);

    Rank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank record);

    int updateByPrimaryKey(Rank record);
}