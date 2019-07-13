package org.cst.buildingnotice.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.service.ArchImgService;
import org.cst.buildingnotice.service.ArchiveService;
import org.cst.buildingnotice.service.DamageService;
import org.cst.buildingnotice.service.RankService;
import org.cst.buildingnotice.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/create", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> create(@RequestBody String jsonstring, 
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
		Integer layer = archive.getIntValue("layer");
		String createyear = archive.getString("createyear");
		int typeid = archive.getIntValue("typeid");
		String body1 = archive.getString("body1");
		String body2 = archive.getString("body2");
		String body3 = archive.getString("body3");
		String remark = archive.getString("remark");
		List<Integer> imgs = archive.getJSONArray("imgs").toJavaList(Integer.class);
		List<Integer> damage = archive.getJSONArray("damage").toJavaList(Integer.class);
		
		double rankratio = damageService.ratio(damage);
		int rankid = rankService.getIdByRatio(rankratio);
		
		String advise = typeService.getAdviseByIdAndBody3(typeid, body3);
		
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = createyear != null ? dateformat.parse(createyear) : null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO userid
		int archid = archiveService.create(unit, phone, material, addr, hold, 
				holdid, attr, layer, date, typeid, body1, body2, body3, rankid, 
				rankratio, advise, null, remark, 1);
				//(Integer) model.asMap().get("userid"));
		
		for (int i = 0; i < imgs.size(); i++) {
			archImgService.create(archid, imgs.get(i));
		}
		
		damageService.create(archid, damage);
		
		Map<String, Object> dict = new HashMap<String, Object>();
		dict.put("status", HttpStatus.OK.value());
		dict.put("archid", archid);

		return dict;
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public Map<String, Object> download(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject archive = JSONObject.parseObject(jsonstring);
		List<Integer> ids = archive.getJSONArray("ids").toJavaList(Integer.class);
		
		
		return null;
	}
}
