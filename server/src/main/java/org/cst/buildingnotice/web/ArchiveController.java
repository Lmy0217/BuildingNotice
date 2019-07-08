package org.cst.buildingnotice.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.service.ArchImgService;
import org.cst.buildingnotice.service.ArchiveService;
import org.cst.buildingnotice.service.DamageService;
import org.cst.buildingnotice.service.RankService;
import org.cst.buildingnotice.service.Type1Service;
import org.cst.buildingnotice.service.Type2Service;
import org.cst.buildingnotice.service.Type3Service;
import org.cst.buildingnotice.service.Type4Service;
import org.cst.buildingnotice.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@RequestMapping("/archive")
@Controller
public class ArchiveController {

	@Autowired
	private ArchiveService archiveService;

	@Autowired
	private ArchImgService archImgService;

	@Autowired
	private DamageService damageService;

	@Autowired
	private RankService rankService;
	
	@Autowired
	private TypeService typeService;

	@Autowired
	private Type1Service type1Service;

	@Autowired
	private Type2Service type2Service;

	@Autowired
	private Type3Service type3Service;

	@Autowired
	private Type4Service type4Service;

	@RequestMapping(value = "/create", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> create(@RequestBody String jsonstring, 
//			@RequestParam("unit") String unit,
//			@RequestParam(value = "phone", required = false) String phone,
//			@RequestParam(value = "material", required = false) String material, 
//			@RequestParam("addr") String addr,
//			@RequestParam("hold") String hold, 
//			@RequestParam("holdid") String holdid, 
//			@RequestParam("attr") String attr,
//			@RequestParam("layer") int layer, 
//			@RequestParam(value = "createyear", required = false) Integer createyear,
//			@RequestParam("typeid") int typeid, 
//			@RequestParam("imgs[]") List<Integer> imgs,
//			@RequestParam("damage[]") List<Integer> damage, 
//			@RequestParam("type[]") List<Integer> type,
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject archive = JSONObject.parseObject(jsonstring);
		String unit = archive.getString("unit");
		String phone = archive.getString("phone");
		String material = archive.getString("material");
		String addr = archive.getString("addr");
		String hold = archive.getString("hold");
		String holdid = archive.getString("holdid");
		String attr = archive.getString("attr");
		int layer = archive.getIntValue("layer");
		Integer createyear = archive.getInteger("createyear");
		int typeid = archive.getIntValue("typeid");
		List<Integer> imgs = archive.getJSONArray("imgs").toJavaList(Integer.class);
		List<Integer> damage = archive.getJSONArray("damage").toJavaList(Integer.class);
		List<Integer> type = archive.getJSONArray("type").toJavaList(Integer.class);

		double rankratio = damageService.ratio(damage);
		int rankid = rankService.getIdByRatio(rankratio);
		Calendar calendar = Calendar.getInstance();
		if (createyear != null) {
			calendar.set(Calendar.YEAR, createyear);
		}
		
		// TODO create SQL error
		// TODO userid
		int archid = archiveService.create(unit, phone, material, addr, hold, holdid, attr, layer,
				createyear != null ? calendar.getTime() : null, typeid, null, rankid, rankratio,
				1);//(Integer) model.asMap().get("userid"));
		
		for (int i = 0; i < imgs.size(); i++) {
			archImgService.create(archid, imgs.get(i));
		}
		
		damageService.create(archid, damage);
		
		String typetabel = typeService.getTypeById(typeid).getTabel();
		
		switch (typetabel) {
			case "type1":
				type1Service.create(archid, type);
				break;
			case "type2":
				type2Service.create(archid, type);
				break;
			case "type3":
				type3Service.create(archid, type);
				break;
			case "type4":
				type4Service.create(archid, type);
				break;
		}
		
		Map<String, Object> dict = new HashMap<String, Object>();
		dict.put("code", HttpStatus.OK);
		dict.put("archid", archid);

		return dict;
	}
}
