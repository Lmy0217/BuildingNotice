package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Type4;

public interface Type4Service {

	public int create(int archid, int a21, int a22, int a23, int a24);
	
	public int create(int archid, List<Integer> type4);
	
	public Type4 getType4ByArchid(int archid);
}
