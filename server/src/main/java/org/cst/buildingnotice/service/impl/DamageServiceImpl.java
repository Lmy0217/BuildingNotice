package org.cst.buildingnotice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
		return (3.5 * a112 + 2.7 * a122 + 1.8 * a132 + 1.9 * a212 + 1.9 * a222 + 1.4 * a312 + 2.7 * a412 + 1.0 * a512 + 1.0 * a612)
				/ (3.5 * a111 + 2.7 * a121 + 1.8 * a131 + 1.9 * a211 + 1.9 * a221 + 1.4 * a311 + 2.7 * a411 + 1.0 * a511 + 1.0 * a611);
	}

	public double ratio(List<Integer> damage) {
		List<Double> alpha = new ArrayList<Double>(
				Arrays.asList(3.5, 2.7, 1.8, 1.9, 1.9, 1.4, 2.7, 1.0, 1.0));
		double danger = 0, all = 0;
		for (int i = 0; i < damage.size() - damage.size() % 2; i += 2) {
			all += damage.get(i) * alpha.get((i / 2));
			danger += damage.get(i + 1) * alpha.get((i / 2));
		}
		return danger / all;
	}
}
