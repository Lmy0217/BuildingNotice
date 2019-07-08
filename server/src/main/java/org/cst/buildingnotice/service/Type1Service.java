package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Type1;

public interface Type1Service {

	public int create(int archid, int a1, int a21, int a22, int a23);
	
	public int create(int archid, List<Integer> type1);
	
	public Type1 getType2ByArchid(int archid);
}
