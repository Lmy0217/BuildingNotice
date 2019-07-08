package org.cst.buildingnotice.service.impl;

import org.cst.buildingnotice.dao.AdviseMapper;
import org.cst.buildingnotice.entity.Advise;
import org.cst.buildingnotice.service.AdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdviseService")
public class AdviseServiceImpl implements AdviseService {
	
	@Autowired
	private AdviseMapper adviseMapper;

	public int create(String name, String depict) {
		Advise advise = new Advise();
		advise.setName(name);
		advise.setDepict(depict);
		return adviseMapper.insertSelective(advise);
	}

	public Advise getAdviseByName(String name) {
		return adviseMapper.selectByPrimaryKey(name);
	}
}
