package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.Type;

public interface TypeService {

	public int create(String name, String body1, String body2, String body3, 
			String advise);

	public Type getTypeById(int id);
	
	public String getAdviseByIdAndBody3(int id, String body2);
}
