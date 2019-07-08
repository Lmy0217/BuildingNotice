package org.cst.buildingnotice.service.impl;

import java.util.Date;

import org.cst.buildingnotice.dao.ArchiveMapper;
import org.cst.buildingnotice.entity.Archive;
import org.cst.buildingnotice.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ArchiveService")
public class ArchiveServiceImpl implements ArchiveService {
	
	@Autowired
	private ArchiveMapper archiveMapper;

	public int create(String unit, String phone, String material, String addr, String hold, String holdid, String attr,
			int layer, Date createyear, int typeid, Date identitytime, int rankid, double rankratio, int userid) {
		Archive archive = new Archive();
		archive.setUnit(unit);
		archive.setPhone(phone != null ? phone : "");
		archive.setMaterial(material != null ? material : "");
		archive.setAddr(addr);
		archive.setHold(hold);
		archive.setHoldid(holdid);
		archive.setAttr(attr);
		archive.setLayer(layer);
		archive.setCreateyear(createyear);
		archive.setTypeid(typeid);
		archive.setIdentitytime(identitytime);
		archive.setRankid(rankid);
		archive.setRankratio(rankratio);
		archive.setUserid(userid);
		archiveMapper.insertSelective(archive);
		return archive.getId();
	}

	public Archive getArchiveById(Integer id) {
		return archiveMapper.selectByPrimaryKey(id);
	}
}
