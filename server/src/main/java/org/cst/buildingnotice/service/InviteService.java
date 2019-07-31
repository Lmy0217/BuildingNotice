package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Invite;

public interface InviteService {
	
	public int create(Integer createid, int count);
	
	public List<Invite> getInvitesByCreateid(Integer createid);
}
