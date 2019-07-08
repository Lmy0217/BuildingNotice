package org.cst.buildingnotice.service;

import java.util.List;

import org.cst.buildingnotice.entity.Damage;

public interface DamageService {

	public int create(int archid, int a111, int a112, int a121, int a122, int a131, int a132, int a211, int a212, 
			int a221, int a222, int a311, int a312, int a411, int a412, int a511, int a512, int a611, int a612);
	
	public int create(int archid, List<Integer> damage);
	
	public Damage getDamageByArchid(int archid);
	
	public double ratio(int a111, int a112, int a121, int a122, int a131, int a132, int a211, int a212, int a221,
			int a222, int a311, int a312, int a411, int a412, int a511, int a512, int a611, int a612);
	
	public double ratio(List<Integer> damage);
}
