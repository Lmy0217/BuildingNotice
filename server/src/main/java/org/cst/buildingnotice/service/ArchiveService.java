package org.cst.buildingnotice.service;

import java.util.Date;

import org.cst.buildingnotice.entity.Archive;

public interface ArchiveService {
	
	public int create(String unit, String phone, String material, String addr, String hold, String holdid, String attr, 
			int layer, Date createyear, int typeid, Date identitytime, int rankid, double rankratio, int userid);
	
	public Archive getArchiveById(Integer id);
}
