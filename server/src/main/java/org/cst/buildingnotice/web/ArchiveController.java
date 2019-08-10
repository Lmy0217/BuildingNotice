package org.cst.buildingnotice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.config.Config;
import org.cst.buildingnotice.config.Template;
import org.cst.buildingnotice.entity.ArchiveWithBLOBs;
import org.cst.buildingnotice.entity.Damage;
import org.cst.buildingnotice.entity.Image;
import org.cst.buildingnotice.entity.Rank;
import org.cst.buildingnotice.entity.TypeWithBLOBs;
import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.ArchImgService;
import org.cst.buildingnotice.service.ArchiveService;
import org.cst.buildingnotice.service.DamageService;
import org.cst.buildingnotice.service.ImageService;
import org.cst.buildingnotice.service.RankService;
import org.cst.buildingnotice.service.TypeService;
import org.cst.buildingnotice.service.UserService;
import org.cst.buildingnotice.util.ExceptionUtil;
import org.cst.buildingnotice.util.FileUtil;
import org.cst.buildingnotice.util.SecurityUtil;
import org.cst.buildingnotice.util.StringUtil;
import org.cst.buildingnotice.util.TemplateUtil;
import org.cst.buildingnotice.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
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
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/create", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> create(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String hexToken = json.getString("token");
		String unit = json.getString("unit");
		String phone = json.getString("phone");
		String material = json.getString("material");
		String addr = json.getString("addr");
		String hold = json.getString("hold");
		String holdid = json.getString("holdid");
		String attr = json.getString("attr");
		Integer layer = json.getIntValue("layer");
		String createyear = json.getString("createyear");
		Integer typeid = json.getInteger("typeid");
		String body1 = json.getString("body1");
		String body2 = json.getString("body2");
		String body3 = json.getString("body3");
		String remark = json.getString("remark");
		JSONArray imgsJSON = json.getJSONArray("imgs");
		JSONArray damageJSON = json.getJSONArray("damage");
		if (hexToken == null || typeid == null || imgsJSON == null || damageJSON == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String token = StringUtil.hex2String(hexToken);
		Integer userId = SecurityUtil.getIdInToken(token);
		if (userId == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		
		if (user.getToken() == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.UNAUTHORIZED, "未登录！");
		}
		Boolean verifyFlag = SecurityUtil.verifyToken(token, StringUtil.hex2String(user.getToken()));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgMap(HttpStatus.UNAUTHORIZED, "Token 失效！");
		}
		
		if (user.getRole() < Config.ROLE_BAISE) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		List<Integer> imgs = imgsJSON.toJavaList(Integer.class);
		List<Integer> damage = damageJSON.toJavaList(Integer.class);
		
		double rankratio = damageService.ratio(damage);
		int rankid = rankService.getIdByRatio(rankratio);
		List<String> bodyTransform = TemplateUtil.transform(typeid, body1, body2, body3);
		if (bodyTransform == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "转换错误！");
		}
		
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = createyear != null ? dateformat.parse(createyear) : null;
		} catch (ParseException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "参数错误！");
		}
		
		Integer archid = archiveService.create(unit, phone, material, addr, hold, 
				holdid, attr, layer, date, typeid, bodyTransform.get(0), 
				bodyTransform.get(1), bodyTransform.get(2), rankid, 
				rankratio, bodyTransform.get(3), null, remark, userId, null);
		if (archid == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		int flag = -1;
		for (int i = 0; i < imgs.size(); i++) {
			flag = archImgService.create(archid, imgs.get(i));
			if (flag != 1) {
				return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
			}
		}
		
		flag = damageService.create(archid, damage);
		if (flag != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}

		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("archid", archid);
			}
		};
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> download(
			@RequestParam(value="json", required=false) String jsonstring,
			HttpServletRequest request, Model model) {
		
		if (jsonstring == null) {
			return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "请求错误！");
		}
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		Integer type = json.getInteger("type");
		JSONArray idsJson = json.getJSONArray("ids");
		String hexToken = json.getString("token");
		if (type == null && idsJson == null || hexToken == null) {
			return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String token = StringUtil.hex2String(hexToken);
		Integer userId = SecurityUtil.getIdInToken(token);
		if (userId == -1) {
			return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		
		if (user.getToken() == null) {
			return ExceptionUtil.getMsgEntity(HttpStatus.UNAUTHORIZED, "未登录！");
		}
		Boolean verifyFlag = SecurityUtil.verifyToken(token, StringUtil.hex2String(user.getToken()));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgEntity(HttpStatus.UNAUTHORIZED, "Token 失效！");
		}
		
		if (user.getRole() < Config.ROLE_BAISE) {
			return ExceptionUtil.getMsgEntity(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		// TODO type download
		List<Integer> ids = new ArrayList<Integer>();
		List<ArchiveWithBLOBs> archs = null;
		if (type != null && (type == 0 || type == 1 || type == 2)) {
			archs = archiveService.getArchivesByUserid(userId);
			for (int idx = 0; idx < archs.size(); idx++) {
				if (type == 0 || archs.get(idx).getStatus() + 1 == type) {
					ids.add(archs.get(idx).getId());
				}
			}
		} else if (idsJson != null) {
			ids = idsJson.toJavaList(Integer.class);
		} else {
			return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String archive_path = FileUtil.getRealPath(request, Config.PATH_ARCHIVE);
		String imgs_path = FileUtil.getRealPath(request, Config.PATH_IMAGE);
		String template_path = FileUtil.getRealPath(request, Config.PATH_TEMPLATE);
		if (archive_path == null || imgs_path == null || template_path == null) {
			return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "服务器路径错误！");
		}
		
		DateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat yearformat = new SimpleDateFormat("yyyy");
		List<String> files = new ArrayList<String>();
		List<ArchiveWithBLOBs> archiveList = new ArrayList<ArchiveWithBLOBs>();
		
		for (int i = 0; i < ids.size(); i++) {
			
			int id = ids.get(i);
			
			ArchiveWithBLOBs archiveWithBLOBs = archiveService.getArchiveById(id);
			archiveList.add(archiveWithBLOBs);
			if (archiveWithBLOBs == null) {
				return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "报告不存在！");
			}
			if (archiveWithBLOBs.getUserid() != userId) {
				return ExceptionUtil.getMsgEntity(HttpStatus.FORBIDDEN, "权限禁止！");
			}
			Damage damage = damageService.getDamageByArchid(id);
			if (damage == null) {
				return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "报告缺少必要信息！");
			}
			TypeWithBLOBs typeWithBLOBs = typeService.getTypeById(archiveWithBLOBs.getTypeid());
			Rank rank = rankService.getRankById(archiveWithBLOBs.getRankid());
			if (typeWithBLOBs == null || rank == null) {
				return ExceptionUtil.getMsgEntity(HttpStatus.BAD_REQUEST, "类型不存在！");
			}
			List<Image> imgs = imageService.getImagesByIdList(archImgService.getImgsByArchid(id));
			
			String identityTime = dateformat.format(archiveWithBLOBs.getIdentitytime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(archiveWithBLOBs.getIdentitytime());
			calendar.add(Calendar.DATE, 7);
			
			String file_name = String.format("%08d", id) + '_' + archiveWithBLOBs.getHold() + ".docx";
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
					(yearformat.format(archiveWithBLOBs.getCreateyear())) : null);
			data.put("typename", typeWithBLOBs.getName());
			data.put("identitytime", identityTime);
			data.put("body1", TemplateUtil.stringRender(
					typeWithBLOBs.getBody1(), archiveWithBLOBs.getBody1()));
			data.put("body2", TemplateUtil.stringRender(
					typeWithBLOBs.getBody2(), archiveWithBLOBs.getBody2()));
			data.put("body3", TemplateUtil.stringRender(
					typeWithBLOBs.getBody3(), archiveWithBLOBs.getBody3()));
			
			for (int j = 0; j < imgs.size(); j++) {
				String img_path = imgs_path + File.separator + imgs.get(j).getPath();
				System.out.println(img_path);
				int width = imgs.size() == 1 ? 540 : 250;
				int height = imgs.size() == 1 ? 280 : 115;
				data.put("image" + j, new PictureRenderData(width, height, img_path));
				data.put("imagedepict" + j, imgs.get(j).getDepict());
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
			
			data.put("rankratio", archiveWithBLOBs.getRankratio());
			data.put("rankdepict", rank.getDepict());
			data.put("rankname", rank.getName());
			
			data.put("advise", TemplateUtil.stringRender(
					typeWithBLOBs.getAdvise(), archiveWithBLOBs.getAdvise()));
			
			data.put("year", calendar.get(Calendar.YEAR));
			data.put("month", calendar.get(Calendar.MONTH) + 1);
			data.put("day", calendar.get(Calendar.DAY_OF_MONTH));
			
			data = Template.defaultValue(data);
			
			System.out.println("data complete");

			String template_name = "template_4";
			if (imgs.size() == 1) template_name = "template_1";
			else if (imgs.size() == 0) template_name = "template_0";
			if (rank.getId() == 4) template_name += "D";
			String template_file = template_path + File.separator 
					+ template_name + ".docx";
			if (!TemplateUtil.render(template_file, data, file)) {
				return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "生成错误！");
			}
			files.add(file);
		}
		
		String zip_file_name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(new Date()) + "_" + String.format("%08d", userId) + ".zip";
		String zip_path = FileUtil.getRealPath(request, Config.PATH_ZIP);
		if (zip_path == null) {
			return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "服务器路径错误！");
		}
		String zip_file = zip_path + File.separator + zip_file_name;
		
		if (!ZipUtil.zip(files, zip_file)) {
			return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "压缩错误！");
		}
		
		InputStream in = null;;
		byte[] body = null;
		try {
			in = new FileInputStream(new File(zip_file));
			body = new byte[in.available()];
			in.read(body);
		} catch (Exception e) {
			return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "IO 错误！");
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "IO 错误！");
			}
		}
		
		for (int i = 0; i < archiveList.size(); i++) {
			ArchiveWithBLOBs archiveWithBLOBs = archiveList.get(i);
			archiveWithBLOBs.setStatus(Config.STATUS_ARCHIVE_DOWNED);
			int flag = archiveService.updateByPrimaryKeyWithBLOBs(archiveWithBLOBs);
			if (flag != 1) {
				return ExceptionUtil.getMsgEntity(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
			}
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=" + zip_file_name);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		JSONArray idsJson = json.getJSONArray("ids");
		String hexToken = json.getString("token");
		if (idsJson == null || hexToken == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String token = StringUtil.hex2String(hexToken);
		Integer userId = SecurityUtil.getIdInToken(token);
		if (userId == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		
		if (user.getToken() == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.UNAUTHORIZED, "未登录！");
		}
		Boolean verifyFlag = SecurityUtil.verifyToken(token, StringUtil.hex2String(user.getToken()));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgMap(HttpStatus.UNAUTHORIZED, "Token 失效！");
		}
		
		if (user.getRole() < Config.ROLE_BAISE) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		List<Integer> ids = idsJson.toJavaList(Integer.class);
		int count = archiveService.deleteByIdsAndUserid(ids, userId);
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("count", count);
			}
		};
	}
	
	@RequestMapping(value="/list", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		Integer type = json.getInteger("type");
		Integer page = json.getInteger("page");
		String hexToken = json.getString("token");
		if (hexToken == null || type == null || page == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String token = StringUtil.hex2String(hexToken);
		Integer userId = SecurityUtil.getIdInToken(token);
		if (userId == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		
		if (user.getToken() == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.UNAUTHORIZED, "未登录！");
		}
		Boolean verifyFlag = SecurityUtil.verifyToken(token, StringUtil.hex2String(user.getToken()));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgMap(HttpStatus.UNAUTHORIZED, "Token 失效！");
		}
		
		if (user.getRole() < Config.ROLE_BAISE) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		List<ArchiveWithBLOBs> archs = archiveService.getArchivesByUserid(userId);
		for (int i = archs.size() - 1; i >= 0; i--) {
			if (archs.get(i).getStatus() == -1 || 
					((type == Config.STATUS_ARCHIVE_NODOWN + 1 || 
					type == Config.STATUS_ARCHIVE_DOWNED + 1) 
							&& archs.get(i).getStatus() + 1 != type)) {
				archs.remove(i);
			}
		}
		int idxStart = (page - 1) * Config.COUNT_PAGE_ITEM;
		int idxStop = idxStart + Config.COUNT_PAGE_ITEM;
		idxStop = idxStop > archs.size() ? archs.size() : idxStop;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
		for (int i = idxStart; i < idxStop; i++) {
			HashMap<String, Object> jsonObject = new HashMap<String, Object>();
			ArchiveWithBLOBs archiveWithBLOBs = archs.get(i);
			jsonObject.put("id", archiveWithBLOBs.getId());
			jsonObject.put("title", dateFormat.format(archiveWithBLOBs.getIdentitytime()) 
					+ " " + archiveWithBLOBs.getHold());
			jsonObject.put("date", dateFormat.format(archiveWithBLOBs.getIdentitytime()));
			jsonList.add(jsonObject);
		}
		
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("status", HttpStatus.OK.value());
		jsonResult.put("count", archs.size());
		jsonResult.put("list", jsonList);
		
		return jsonResult;
	}
}
