package org.cst.buildingnotice.service.impl;

import java.util.List;

import org.cst.buildingnotice.dao.Type3Mapper;
import org.cst.buildingnotice.entity.Type3;
import org.cst.buildingnotice.service.Type3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("Type3Service")
public class Type3ServiceImpl implements Type3Service {
	
	@Autowired
	private Type3Mapper type3Mapper;

	public int create(int archid, int a21, int a22, int a23, int a24) {
		Type3 type3 = new Type3();
		type3.setArchid(archid);
		type3.setA21(a21);
		type3.setA22(a22);
		type3.setA23(a23);
		type3.setA24(a24);
		return type3Mapper.insertSelective(type3);
	}
	
	public int create(int archid, List<Integer> type3) {
		return create(archid, type3.get(0), type3.get(1), type3.get(2), type3.get(3));
	}

	public Type3 getType3ByArchid(int archid) {
		return type3Mapper.selectByPrimaryKey(archid);
	}
}
