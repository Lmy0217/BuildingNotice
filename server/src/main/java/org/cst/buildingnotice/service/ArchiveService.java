package org.cst.buildingnotice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cst.buildingnotice.entity.ArchiveWithBLOBs;

public interface ArchiveService {

	public Integer create(String unit, String phone, String material, String addr, String hold, String holdid, String attr,
			Integer layer, Date createyear, Integer typeid, String body1, String body2, String body3, Integer rankid,
			Double rankratio, String advise, Date identitytime, String remark, Integer userid, Integer status);

	public ArchiveWithBLOBs getArchiveById(Integer id);
	
	public List<ArchiveWithBLOBs> getArchivesByUserid(Integer userid);
	
	public int updateByPrimaryKeyWithBLOBs(ArchiveWithBLOBs record);
	
	public int countByUserid(Integer userid);
	
	public List<Map<String, Object>> statusCountByUserid(Integer userid);
	
	public int deleteByIdsAndUserid(List<Integer> ids, Integer userid);
}
