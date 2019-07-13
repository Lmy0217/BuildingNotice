package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.TypeWithBLOBs;

public interface TypeService {

	public int create(String name, String body1, String body2, String body3, 
			String advise);

	public TypeWithBLOBs getTypeById(int id);
	
	public String getAdviseByIdAndBody3(int id, String body3);
}
