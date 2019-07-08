package org.cst.buildingnotice.service.impl;

import java.util.List;

import org.cst.buildingnotice.dao.Type4Mapper;
import org.cst.buildingnotice.entity.Type4;
import org.cst.buildingnotice.service.Type4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("Type4Service")
public class Type4ServiceImpl implements Type4Service {
	
	@Autowired
	private Type4Mapper type4Mapper;

	public int create(int archid, int a21, int a22, int a23, int a24) {
		Type4 type4 = new Type4();
		type4.setArchid(archid);
		type4.setA21(a21);
		type4.setA22(a22);
		type4.setA23(a23);
		type4.setA24(a24);
		return type4Mapper.insertSelective(type4);
	}
	
	public int create(int archid, List<Integer> type4) {
		return create(archid, type4.get(0), type4.get(1), type4.get(2), type4.get(3));
	}

	public Type4 getType4ByArchid(int archid) {
		return type4Mapper.selectByPrimaryKey(archid);
	}
}
