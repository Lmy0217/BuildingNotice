package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.Type;

public interface TypeService {

	public int create(String name, String body2, String body3, String tabel);
	
	public Type getTypeById(int id);
}
