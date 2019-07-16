package org.cst.buildingnotice.service;

import java.util.List;

public interface ArchImgService {

	public int create(int archid, int imgid);
	
	public List<Integer> getImgsByArchid(int archid);
	
	public List<Integer> getArchsByImgid(int imgid);
}
