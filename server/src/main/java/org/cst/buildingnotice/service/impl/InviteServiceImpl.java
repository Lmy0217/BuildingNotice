package org.cst.buildingnotice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cst.buildingnotice.dao.InviteMapper;
import org.cst.buildingnotice.entity.Invite;
import org.cst.buildingnotice.service.InviteService;
import org.cst.buildingnotice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("InviteService")
public class InviteServiceImpl implements InviteService {
	
	@Autowired
	private InviteMapper inviteMapper;

	public int create(Integer createid, int count) {
		
		List<String> codes = new ArrayList<String>();
		if (count == 0) return 0;
		
		for (int i = 0; i < count; i++) {
			codes.add(SecurityUtil.getShortUUID());
		}
		
		return inviteMapper.insertCodes(codes, createid);
	}

	public List<Invite> getInvitesByCreateid(Integer createid) {
		return inviteMapper.selectByCreateid(createid);
	}
}
