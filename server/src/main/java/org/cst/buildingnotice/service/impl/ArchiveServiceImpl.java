package org.cst.buildingnotice.service.impl;

import java.util.Date;
import java.util.List;

import org.cst.buildingnotice.dao.ArchiveMapper;
import org.cst.buildingnotice.entity.ArchiveWithBLOBs;
import org.cst.buildingnotice.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ArchiveService")
public class ArchiveServiceImpl implements ArchiveService {

	@Autowired
	private ArchiveMapper archiveMapper;

	public Integer create(String unit, String phone, String material, String addr, String hold, String holdid, String attr,
			Integer layer, Date createyear, Integer typeid, String body1, String body2, String body3, Integer rankid,
			Double rankratio, String advise, Date identitytime, String remark, Integer userid, Integer status) {
		ArchiveWithBLOBs archiveWithBLOBs = new ArchiveWithBLOBs();
		archiveWithBLOBs.setUnit(unit != null ? unit : "");
		archiveWithBLOBs.setPhone(phone != null ? phone : "");
		archiveWithBLOBs.setMaterial(material != null ? material : "");
		archiveWithBLOBs.setAddr(addr != null ? addr : "");
		archiveWithBLOBs.setHold(hold != null ? hold : "");
		archiveWithBLOBs.setHoldid(holdid != null ? holdid : "");
		archiveWithBLOBs.setAttr(attr != null ? attr : "");
		archiveWithBLOBs.setLayer(layer);
		archiveWithBLOBs.setCreateyear(createyear);
		archiveWithBLOBs.setTypeid(typeid);
		archiveWithBLOBs.setBody1(body1 != null ? body1 : "");
		archiveWithBLOBs.setBody2(body2 != null ? body2 : "");
		archiveWithBLOBs.setBody3(body3 != null ? body3 : "");
		archiveWithBLOBs.setRankid(rankid);
		archiveWithBLOBs.setRankratio(rankratio);
		archiveWithBLOBs.setAdvise(advise != null ? advise : "");
		archiveWithBLOBs.setIdentitytime(identitytime);
		archiveWithBLOBs.setRemark(remark != null ? remark : "");
		archiveWithBLOBs.setUserid(userid);
		archiveWithBLOBs.setStatus(status);
		archiveMapper.insertSelective(archiveWithBLOBs);
		return archiveWithBLOBs.getId();
	}

	public ArchiveWithBLOBs getArchiveById(Integer id) {
		return archiveMapper.selectByPrimaryKey(id);
	}
	
	public List<ArchiveWithBLOBs> getArchivesByUserid(Integer userid) {
		return archiveMapper.selectByUserid(userid);
	}

	public int updateByPrimaryKeyWithBLOBs(ArchiveWithBLOBs record) {
		return archiveMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	public int countByUserid(Integer userid) {
		return archiveMapper.countByUserid(userid);
	}

	public List<Integer> statusCountByUserid(Integer userid) {
		return archiveMapper.statusCountByUserid(userid);
	}
}
