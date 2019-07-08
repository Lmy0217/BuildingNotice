package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.Advise;

public interface AdviseService {

	public int create(String name, String depict);
	
	public Advise getAdviseByName(String name);
}
