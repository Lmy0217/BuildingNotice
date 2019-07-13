package org.cst.buildingnotice.web;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import org.cst.buildingnotice.entity.ArchiveWithBLOBs;
import org.cst.buildingnotice.entity.Damage;
import org.cst.buildingnotice.entity.Image;
import org.cst.buildingnotice.entity.Rank;
import org.cst.buildingnotice.entity.TypeWithBLOBs;
import org.cst.buildingnotice.service.ArchImgService;
import org.cst.buildingnotice.service.ArchiveService;
import org.cst.buildingnotice.service.DamageService;
import org.cst.buildingnotice.service.ImageService;
import org.cst.buildingnotice.service.RankService;
import org.cst.buildingnotice.service.TypeService;
import org.cst.buildingnotice.util.Template;
import org.cst.buildingnotice.util.Zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.PictureRenderData;

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
	private ImageService imageService;

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
	
	@RequestMapping(value = "/download", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> download(@RequestBody String jsonstring,
			HttpServletRequest request, Model model) throws IOException {
		
		System.out.println(jsonstring);
		
		JSONObject json = JSONObject.parseObject(jsonstring);
		List<Integer> ids = json.getJSONArray("ids").toJavaList(Integer.class);
		
		String archive_path = request.getServletContext().getRealPath("/downloads/archive");
		System.out.println("archive_path :" + archive_path);
		File archive_path_file = new File(archive_path);
		if (!archive_path_file.exists()) {
			System.out.println(archive_path_file.mkdirs());
		}
		
		String imgs_path = request.getServletContext().getRealPath("/uploads/images");
		System.out.println("imgs_path :" + imgs_path);
		
		String template_path = request.getServletContext().getRealPath("/uploads/template");
		System.out.println("template_path :" + template_path);
		// TODO
		File template_path_file = new File(template_path);
		if (!template_path_file.exists()) {
			System.out.println(template_path_file.mkdirs());
		}
		String template_file = template_path + File.separator + "template_2.docx";
		System.out.println("template_file :" + template_file);
		
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> files = new ArrayList<String>();
		
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			ArchiveWithBLOBs archiveWithBLOBs = archiveService.getArchiveById(id);
			List<Image> imgs = imageService.getImagesByIdList(
					archImgService.getImgsByArchid(id));
			Damage damage = damageService.getDamageByArchid(id);
			TypeWithBLOBs typeWithBLOBs = typeService.getTypeById(
					archiveWithBLOBs.getTypeid());
			Rank rank = rankService.getRankById(archiveWithBLOBs.getRankid());
			
			String identityTime = dateformat.format(archiveWithBLOBs.getIdentitytime());
			String file_name = identityTime + '_' + String.format("%s", id) + ".docx";
			String file = archive_path + File.separator + file_name;
			System.out.println("file :" + file);
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("unit", archiveWithBLOBs.getUnit());
			data.put("phone", archiveWithBLOBs.getPhone());
			data.put("material", archiveWithBLOBs.getMaterial());
			data.put("addr", archiveWithBLOBs.getAddr());
			data.put("hold", archiveWithBLOBs.getHold());
			data.put("holdid", archiveWithBLOBs.getHoldid());
			data.put("attr", archiveWithBLOBs.getAttr());
			data.put("layer", archiveWithBLOBs.getLayer());
			data.put("createyear", archiveWithBLOBs.getCreateyear() != null ? 
					dateformat.format(archiveWithBLOBs.getCreateyear()) : "");
			data.put("typename", typeWithBLOBs.getName());
			data.put("identitytime", identityTime);
			data.put("body1", Template.stringRender(typeWithBLOBs.getBody1(), "1;"));
			data.put("body2", Template.stringRender(typeWithBLOBs.getBody2(), archiveWithBLOBs.getBody2()));
			data.put("body3", Template.stringRender(typeWithBLOBs.getBody3(), archiveWithBLOBs.getBody3()));
			
			for (int j = 0; j < 1; j++) {
				String img_path = imgs_path + File.separator + imgs.get(j).getPath();
				System.out.println(img_path);
				data.put("image", new PictureRenderData(560, 310, img_path));
				data.put("imagedepict", imgs.get(i).getDepict());
			}
			
			data.put("a111", damage.getA111());
			data.put("a112", damage.getA112());
			data.put("a121", damage.getA121());
			data.put("a122", damage.getA122());
			data.put("a131", damage.getA131());
			data.put("a132", damage.getA132());
			data.put("a211", damage.getA211());
			data.put("a212", damage.getA212());
			data.put("a221", damage.getA221());
			data.put("a222", damage.getA222());
			data.put("a311", damage.getA311());
			data.put("a312", damage.getA312());
			data.put("a411", damage.getA411());
			data.put("a412", damage.getA412());
			data.put("a511", damage.getA511());
			data.put("a512", damage.getA512());
			data.put("a611", damage.getA611());
			data.put("a612", damage.getA612());
			
			data.put("rankratio", archiveWithBLOBs.getRankratio() * 100);
			data.put("rankdepict", rank.getDepict());
			data.put("rankname", rank.getName());
			
			data.put("advise", Template.stringRender(typeWithBLOBs.getAdvise(), 
					typeService.getAdviseByIdAndBody3(typeWithBLOBs.getId(), archiveWithBLOBs.getBody3())));
			System.out.println("data complete");
			Template.render(template_file, data, file);
			files.add(file);
		}
		
		String zip_path = request.getServletContext().getRealPath("/downloads/zip");
		System.out.println("zip_path :" + zip_path);
		File zip_path_file = new File(zip_path);
		if (!zip_path_file.exists()) {
			System.out.println(zip_path_file.mkdirs());
		}
		DateFormat dateformatHms = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String zip_file_name = dateformatHms.format(new Date()) + "_" + "1" + ".zip";
		String zip_file = zip_path + File.separator + zip_file_name;
		System.out.println(zip_file);
		Zip.zip(files, zip_file);
		
		
		Map<String, Object> dict = new HashMap<String, Object>();
		dict.put("status", HttpStatus.OK.value());
		dict.put("zip", zip_file);

		return dict;
	}
}
