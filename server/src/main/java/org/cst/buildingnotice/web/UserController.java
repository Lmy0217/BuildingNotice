package org.cst.buildingnotice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.ArchiveService;
import org.cst.buildingnotice.service.UserService;
import org.cst.buildingnotice.util.ExceptionUtil;
import org.cst.buildingnotice.util.SecurityUtil;
import org.cst.buildingnotice.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@RequestMapping(value="/create", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
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
		
		String name = json.getString("name");
		String pwd = json.getString("pwd");
		if (name == null || pwd == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		List<User> userList = userService.getUserByName(name);
		if (userList.size() != 0) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "用户名已存在！");
		}
		if (pwd.length() < 8) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "密码长度小于 8 位！");
		}
		
		String salt = SecurityUtil.saltGenerate();
		pwd = SecurityUtil.encrypt(pwd, salt);
		Integer id = userService.create(name, StringUtil.string2Hex(pwd), StringUtil.string2Hex(salt));
		if (id == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("userid", id);
			}
		};
	}
	
	@RequestMapping(value="/login", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String name = json.getString("name");
		String pwd = json.getString("pwd");
		if (name == null || pwd == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		List<User> userList = userService.getUserByName(name);
		if (userList.size() != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "用户名或密码错误！");
		}
		User user = userList.get(0);
		
		if(!SecurityUtil.verify(pwd, user.getSalt(), user.getPwd())) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "用户名或密码错误！");
		}
		
		String salt = SecurityUtil.saltGenerate();
		pwd = SecurityUtil.encrypt(pwd, salt);
		List<String> tokenList = SecurityUtil.token(user.getId(), salt);
		user.setSalt(StringUtil.string2Hex(salt));
		user.setPwd(StringUtil.string2Hex(pwd));
		user.setToken(StringUtil.string2Hex(tokenList.get(1)));
		
		int flag = -1;
		try {
			flag = userService.updateById(user);
		} catch (Exception e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		if (flag != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("token", StringUtil.string2Hex(tokenList.get(0)));
				// TODO role system
				put("perm", user.getRole() >= 10 ? 2 : 1);
			}
		};
	}
	
	@RequestMapping(value="/logout", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String hexToken = json.getString("token");
		if (hexToken == null) {
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
		
		user.setToken(null);
		int flag = -1;
		try {
			flag = userService.updateById(user);
		} catch (Exception e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		if (flag != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("msg", "退出成功！");
			}
		};
	}
	
	@RequestMapping(value="/token", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> token(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String hexToken = json.getString("token");
		if (hexToken == null) {
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
		
		List<String> tokenList = SecurityUtil.token(userId, StringUtil.hex2String(user.getSalt()));
		user.setToken(StringUtil.string2Hex(tokenList.get(1)));
		
		int flag = -1;
		try {
			flag = userService.updateById(user);
		} catch (Exception e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		if (flag != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("token", StringUtil.string2Hex(tokenList.get(0)));
			}
		};
	}
	
	@RequestMapping(value="/pwd", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pwd(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String pwd = json.getString("pwd");
		String newPwd = json.getString("newpwd");
		String hexToken = json.getString("token");
		if (pwd == null || newPwd == null || hexToken == null) {
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
		
		if(!SecurityUtil.verify(pwd, user.getSalt(), user.getPwd())) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "密码错误！");
		}
		
		if (newPwd.length() < 8) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "密码长度小于 8 位！");
		}
		
		String salt = SecurityUtil.saltGenerate();
		newPwd = SecurityUtil.encrypt(newPwd, salt);
		List<String> tokenList = SecurityUtil.token(userId, salt);
		user.setSalt(StringUtil.string2Hex(salt));
		user.setPwd(StringUtil.string2Hex(newPwd));
		user.setToken(StringUtil.string2Hex(tokenList.get(1)));
		
		int flag = -1;
		try {
			flag = userService.updateById(user);
		} catch (Exception e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		if (flag != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("token", StringUtil.string2Hex(tokenList.get(0)));
			}
		};
	}
	
	@RequestMapping(value="/role", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> role(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String hexToken = json.getString("token");
		Integer otherUserId = json.getInteger("userid");
		Integer role = json.getInteger("role");
		if (hexToken == null || otherUserId == null || role == null) {
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
		
		if (user.getRole() < 10) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		User otherUser = userService.getUserById(otherUserId);
		if (otherUser == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "用户不存在！");
		}
		
		if (otherUser.getRole() >= user.getRole() || role >= user.getRole()) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		otherUser.setRole(role);
		int flag = -1;
		try {
			flag = userService.updateById(otherUser);
		} catch (Exception e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		if (flag != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
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
		
		String hexToken = json.getString("token");
		Integer page = json.getInteger("page");
		Integer role = json.getInteger("role");
		if (hexToken == null || page == null) {
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
		
		if (role == null) role = user.getRole() - 1;
		
		if (user.getRole() < 10 || user.getRole() <= role || role < 0) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		List<User> users = userService.getUsersByRole(role);
		
		int idxStart = (page - 1) * 15;
		int idxStop = idxStart + 15;
		idxStop = idxStop > users.size() ? users.size() : idxStop;
		
		List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
		for (int i = idxStart; i < idxStop; i++) {
			HashMap<String, Object> jsonObject = new HashMap<String, Object>();
			User u = users.get(i);
			int archCount = archiveService.countByUserid(u.getId());
			jsonObject.put("id", u.getId());
			jsonObject.put("role", u.getRole());
			jsonObject.put("archcount", archCount);
			jsonList.add(jsonObject);
		}
		
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("status", HttpStatus.OK.value());
		jsonResult.put("count", idxStop - idxStart);
		jsonResult.put("list", jsonList);
		
		return jsonResult;
	}
}
