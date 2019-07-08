package org.cst.buildingnotice.service.impl;

import java.util.List;

import org.cst.buildingnotice.dao.Type1Mapper;
import org.cst.buildingnotice.entity.Type1;
import org.cst.buildingnotice.service.Type1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("Type1Service")
public class Type1ServiceImpl implements Type1Service {
	
	@Autowired
	private Type1Mapper type1Mapper;

	public int create(int archid, int a1, int a21, int a22, int a23) {
		Type1 type1 = new Type1();
		type1.setArchid(archid);
		type1.setA1(a1);
		type1.setA21(a21);
		type1.setA22(a22);
		type1.setA23(a23);
		return type1Mapper.insertSelective(type1);
	}
	
	public int create(int archid, List<Integer> type1) {
		return create(archid, type1.get(0), type1.get(1), type1.get(2), type1.get(3));
	}

	public Type1 getType2ByArchid(int archid) {
		return type1Mapper.selectByPrimaryKey(archid);
	}
}
