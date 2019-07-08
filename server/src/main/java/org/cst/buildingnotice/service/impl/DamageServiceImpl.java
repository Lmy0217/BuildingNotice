package org.cst.buildingnotice.service.impl;

import java.util.List;

import org.cst.buildingnotice.dao.DamageMapper;
import org.cst.buildingnotice.entity.Damage;
import org.cst.buildingnotice.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DamageService")
public class DamageServiceImpl implements DamageService {

	@Autowired
	private DamageMapper damageMapper;

	public int create(int archid, int a111, int a112, int a121, int a122, int a131, int a132, int a211, int a212,
			int a221, int a222, int a311, int a312, int a411, int a412, int a511, int a512, int a611, int a612) {
		Damage damage = new Damage();
		damage.setArchid(archid);
		damage.setA111(a111);
		damage.setA112(a112);
		damage.setA121(a121);
		damage.setA122(a122);
		damage.setA131(a131);
		damage.setA132(a132);
		damage.setA211(a211);
		damage.setA212(a212);
		damage.setA221(a221);
		damage.setA222(a222);
		damage.setA311(a311);
		damage.setA312(a312);
		damage.setA411(a411);
		damage.setA412(a412);
		damage.setA511(a511);
		damage.setA512(a512);
		damage.setA611(a611);
		damage.setA612(a612);
		return damageMapper.insertSelective(damage);
	}

	public int create(int archid, List<Integer> damage) {
		return create(archid, damage.get(0), damage.get(1), damage.get(2), damage.get(3), damage.get(4), damage.get(5),
				damage.get(6), damage.get(7), damage.get(8), damage.get(9), damage.get(10), damage.get(11),
				damage.get(12), damage.get(13), damage.get(14), damage.get(15), damage.get(16), damage.get(17));
	}

	public Damage getDamageByArchid(int archid) {
		return damageMapper.selectByPrimaryKey(archid);
	}

	public double ratio(int a111, int a112, int a121, int a122, int a131, int a132, int a211, int a212, int a221,
			int a222, int a311, int a312, int a411, int a412, int a511, int a512, int a611, int a612) {
		return (a112 + a122 + a132 + a212 + a222 + a312 + a412 + a512 + a612)
				/ (double) (a111 + a121 + a131 + a211 + a221 + a311 + a411 + a511 + a611);
	}

	public double ratio(List<Integer> damage) {
		int danger = 0, all = 0;
		for (int i = 0; i < damage.size(); i += 2) {
			all += damage.get(i);
			danger += damage.get(i + 1);
		}
		return danger / (double) all;
	}
}
