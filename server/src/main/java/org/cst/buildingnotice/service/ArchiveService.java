package org.cst.buildingnotice.service;

import java.util.Date;

import org.cst.buildingnotice.entity.ArchiveWithBLOBs;

public interface ArchiveService {

	public Integer create(String unit, String phone, String material, String addr, String hold, String holdid, String attr,
			Integer layer, Date createyear, int typeid, String body1, String body2, String body3, int rankid,
			double rankratio, String advise, Date identitytime, String remark, int userid);

	public ArchiveWithBLOBs getArchiveById(Integer id);
}
