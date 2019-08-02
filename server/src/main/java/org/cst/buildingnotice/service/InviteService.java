package org.cst.buildingnotice.service;

import java.util.HashMap;
import java.util.List;

import org.cst.buildingnotice.entity.Invite;

public interface InviteService {
	
	public int create(Integer createid, int count);
	
	public List<Invite> getInvitesByCreateid(Integer createid);
	
	public Invite getInviteByCode(String code);
	
	public int update(Invite invite);
	
	public List<HashMap<String, Object>> getUsersByCreateidAndStatus(
			Integer createid, Integer status);
	
	public List<String> getAdminNameByInviteid(Integer inviteid);
}
