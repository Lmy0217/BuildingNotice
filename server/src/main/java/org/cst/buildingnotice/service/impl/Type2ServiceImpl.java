package org.cst.buildingnotice.service.impl;

import java.util.List;

import org.cst.buildingnotice.dao.Type2Mapper;
import org.cst.buildingnotice.entity.Type2;
import org.cst.buildingnotice.service.Type2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("Type2Service")
public class Type2ServiceImpl implements Type2Service {

	@Autowired
	private Type2Mapper type2Mapper;

	public int create(int archid, int a221, int a222, int a223, int a31, int a32, int a33, int a34) {
		Type2 type2 = new Type2();
		type2.setArchid(archid);
		type2.setA221(a221);
		type2.setA222(a222);
		type2.setA223(a223);
		type2.setA31(a31);
		type2.setA32(a32);
		type2.setA33(a33);
		type2.setA34(a34);
		return type2Mapper.insertSelective(type2);
	}

	public int create(int archid, List<Integer> type2) {
		return create(archid, type2.get(0), type2.get(1), type2.get(2), type2.get(3), type2.get(4), type2.get(5),
				type2.get(6));
	}

	public Type2 getType2ByArchid(int archid) {
		return type2Mapper.selectByPrimaryKey(archid);
	}
}
