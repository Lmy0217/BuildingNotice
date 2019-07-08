package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Type2;

public interface Type2Service {

	public int create(int archid, int a221, int a222, int a223, int a31, int a32, int a33, int a34);
	
	public int create(int archid, List<Integer> type2);
	
	public Type2 getType2ByArchid(int archid);
}
