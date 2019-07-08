package org.cst.buildingnotice.service.impl;

import org.cst.buildingnotice.dao.ArchImgMapper;
import org.cst.buildingnotice.entity.ArchImg;
import org.cst.buildingnotice.service.ArchImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ArchImgService")
public class ArchImgServiceImpl implements ArchImgService {
	
	@Autowired
	private ArchImgMapper archImgMapper;

	public int create(int archid, int imgid) {
		ArchImg archImg = new ArchImg();
		archImg.setArchid(archid);
		archImg.setImgid(imgid);
		return archImgMapper.insertSelective(archImg);
	}
}
