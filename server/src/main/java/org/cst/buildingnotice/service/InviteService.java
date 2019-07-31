package org.cst.buildingnotice.service;

import java.util.List;

public interface InviteService {

	public int create(String code, Integer createid);
	
	public int create(List<String> codes, Integer createid);
}
