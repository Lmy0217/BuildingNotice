package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Type3;

public interface Type3Service {

	public int create(int archid, int a21, int a22, int a23, int a24);
	
	public int create(int archid, List<Integer> type3);
	
	public Type3 getType3ByArchid(int archid);
}
