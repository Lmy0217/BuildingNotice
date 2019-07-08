package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.Rank;

public interface RankService {

	public int create(int id, String name, String depict);
	
	public Rank getRankById(int id);
	
	public int getIdByRatio(double ratio);
}
