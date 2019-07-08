package org.cst.buildingnotice.service.impl;

import org.cst.buildingnotice.dao.TypeMapper;
import org.cst.buildingnotice.entity.Type;
import org.cst.buildingnotice.entity.TypeWithBLOBs;
import org.cst.buildingnotice.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TypeService")
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeMapper typeMapper;

	public int create(String name, String body2, String body3, String tabel) {
		TypeWithBLOBs typeWithBLOBs = new TypeWithBLOBs();
		typeWithBLOBs.setName(name);
		typeWithBLOBs.setBody2(body2);
		typeWithBLOBs.setBody3(body3);
		typeWithBLOBs.setTabel(tabel);
		typeMapper.insertSelective(typeWithBLOBs);
		return typeWithBLOBs.getId();
	}

	public Type getTypeById(int id) {
		return typeMapper.selectByPrimaryKey(id);
	}
}
