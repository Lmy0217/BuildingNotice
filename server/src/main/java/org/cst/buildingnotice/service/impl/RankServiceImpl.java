package org.cst.buildingnotice.service.impl;

import org.cst.buildingnotice.dao.RankMapper;
import org.cst.buildingnotice.entity.Rank;
import org.cst.buildingnotice.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RankService")
public class RankServiceImpl implements RankService {

	@Autowired
	private RankMapper rankMapper;
	
	public int create(int id, String name, String depict) {
		Rank rank = new Rank();
		rank.setId(id);
		rank.setName(name);
		rank.setDepict(depict);
		return rankMapper.insertSelective(rank);
	}

	public Rank getRankById(int id) {
		return rankMapper.selectByPrimaryKey(id);
	}
	
	public int getIdByRatio(double ratio) {
		if (ratio <= 0.05) {
			return 2;
		} else if (ratio <= 0.25) {
			return 3;
		} else {
			return 4;
		}
	}
}
