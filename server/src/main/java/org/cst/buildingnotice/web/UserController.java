package org.cst.buildingnotice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cst.buildingnotice.config.Config;
import org.cst.buildingnotice.entity.Invite;
import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.ArchiveService;
import org.cst.buildingnotice.service.InviteService;
import org.cst.buildingnotice.service.UserService;
import org.cst.buildingnotice.util.EmailUtil;
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
	
	@Autowired
	private InviteService inviteService;
	
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
		String inviteString = json.getString("invite");
		if (name == null || pwd == null || (Config.USE_INVITE && inviteString == null)) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		Invite invite = null;
		if (Config.USE_INVITE) {
			invite = inviteService.getInviteByCode(inviteString);
			if (invite == null) {
				return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "邀请码无效！");
			} else if (invite.getStatus() != Config.STATUS_INVITE_NOUSE) {
				return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "邀请码已被使用！");
			}
		}

		List<User> userList = userService.getUserByName(name);
		if (userList.size() != 0) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "用户名已存在！");
		}
		if (!Pattern.matches(Config.PATTERN_NAME, name) 
				&& !Pattern.matches(Config.PATTERN_PWD, pwd)) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "用户名或密码不符合要求！");
		}
		
		String salt = SecurityUtil.saltGenerate();
		pwd = SecurityUtil.encrypt(pwd, salt);
		Integer id = userService.create(name, StringUtil.string2Hex(pwd), 
				StringUtil.string2Hex(salt), Config.USE_INVITE ? Config.ROLE_BAISE : Config.ROLE_NOPERM);
		if (id == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}
		
		if (Config.USE_INVITE) {
			invite.setInviteid(id);
			invite.setStatus(Config.STATUS_INVITE_USED);
			int flag = inviteService.update(invite);
			if (flag != 1) {
				return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
			}
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
				put("role", user.getRole());
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
		
		if (!Pattern.matches(Config.PATTERN_PWD, newPwd)) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "新密码不符合要求！");
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
		
		if (user.getRole() < Config.ROLE_SUPERADMIN) {
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
	
	@RequestMapping(value="/email/send", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> emailsend(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		// TODO more test, maybe not work
		HttpSession session = request.getSession();
		Object email_timestamp = session.getAttribute("email_timestamp");
		long timestamp = System.currentTimeMillis();
		if (email_timestamp == null) {
			session.setAttribute("email_timestamp", timestamp);
		} else {
			if (timestamp < (Long) email_timestamp + Config.GAP_EMAIL_SEND) {
				return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "两次邮件发送时间间隔太短！");
			} else {
				session.setAttribute("email_timestamp", timestamp);
			}
		}
		
		String hexToken = json.getString("token");
		String email = json.getString("email");
		if (hexToken == null || email == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		if (!Pattern.matches(Config.PATTERN_EMAIL, email)) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "邮箱地址不正确！");
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
		
		List<String> codeList = SecurityUtil.codeEmail(userId, email);
		if (!EmailUtil.sendVerify(email, user.getName(), Config.EMAIL_VERIFY_URL 
				+ StringUtil.string2Hex(codeList.get(0)))) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "验证邮件发送失败！");
		}
		user.setEmail(email + ";" + StringUtil.string2Hex(codeList.get(1)));
		
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
			}
		};
	}
	
	@RequestMapping(value="/email/verify", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> emailverify(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String hexCode = json.getString("code");
		if (hexCode == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String code = StringUtil.hex2String(hexCode);
		Integer userId = SecurityUtil.getIdInCodeEmail(code);
		if (userId == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 错误！");
		}
		
		String email = user.getEmail();
		if (email == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 失效！");
		}
		int sepIndex = email.indexOf(";");
		if (sepIndex == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 失效！");
		}
		
		Boolean verifyFlag = SecurityUtil.verifyCodeEmail(code, 
				StringUtil.hex2String(email.substring(sepIndex + 1)));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 失效！");
		}
		user.setEmail(email.substring(0, sepIndex));
		
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
			}
		};
	}
	
	@RequestMapping(value="/reset/send", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetsend(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		// TODO more test, maybe not work
		HttpSession session = request.getSession();
		Object email_timestamp = session.getAttribute("email_timestamp");
		long timestamp = System.currentTimeMillis();
		if (email_timestamp == null) {
			session.setAttribute("email_timestamp", timestamp);
		} else {
			if (timestamp < (Long) email_timestamp + Config.GAP_EMAIL_SEND) {
				return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "两次邮件发送时间间隔太短！");
			} else {
				session.setAttribute("email_timestamp", timestamp);
			}
		}
		
		String name = json.getString("name");
		String email = json.getString("email");
		if (name == null && email == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		if ((name == null || !Pattern.matches(Config.PATTERN_NAME, name)) && 
				(email == null || !Pattern.matches(Config.PATTERN_EMAIL, email))) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "用户名或邮箱格式不正确！");
		}
		
		List<User> userList = name != null ? userService.getUserByName(name) : userService.getUserByEmail(email);
		if (userList.size() != 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "用户不存在！");
		}
		User user = userList.get(0);
		
		if (user.getEmail() == null || user.getEmail().indexOf(";") != -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "未绑定邮箱！");
		}
		
		List<String> codeList = SecurityUtil.codeReset(user.getId(), StringUtil.hex2String(user.getPwd()));
		if (!EmailUtil.sendReset(user.getEmail(), user.getName(), 
				Config.RESET_VERIFY_URL + StringUtil.string2Hex(codeList.get(0)))) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "重置邮件发送失败！");
		}
		user.setPwdstatus(StringUtil.string2Hex(codeList.get(1)));
		
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
			}
		};
	}
	
	@RequestMapping(value="/reset/verify", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetverify(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String hexCode = json.getString("code");
		if (hexCode == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String code = StringUtil.hex2String(hexCode);
		Integer userId = SecurityUtil.getIdInCodeReset(code);
		if (userId == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 错误！");
		}
		
		String pwdStatus = user.getPwdstatus();
		if (pwdStatus == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 失效！");
		}
		
		Boolean verifyFlag = SecurityUtil.verifyCodeReset(code, StringUtil.hex2String(pwdStatus));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Code 失效！");
		}
		
		List<String> codeList = SecurityUtil.codeReset(user.getId(), 
				StringUtil.hex2String(user.getPwd()) + StringUtil.hex2String(pwdStatus));
		user.setPwdstatus(StringUtil.string2Hex(codeList.get(1)));
		
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
				put("code", StringUtil.string2Hex(codeList.get(0)));
			}
		};
	}
	
	@RequestMapping(value="/reset/action", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetaction(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "Json 转换错误！");
		}
		
		String pwd = json.getString("pwd");
		String hexCode = json.getString("code");
		if (pwd == null || hexCode == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		
		String code = StringUtil.hex2String(hexCode);
		Integer userId = SecurityUtil.getIdInCodeReset(code);
		if (userId == -1) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 错误！");
		}
		
		String pwdStatus = user.getPwdstatus();
		if (pwdStatus == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 失效！");
		}
		
		Boolean verifyFlag = SecurityUtil.verifyCodeReset(code, StringUtil.hex2String(pwdStatus));
		if (!verifyFlag) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "Token 失效！");
		}
		
		if (!Pattern.matches(Config.PATTERN_PWD, pwd)) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "密码不符合要求！");
		}
		
		String salt = SecurityUtil.saltGenerate();
		pwd = SecurityUtil.encrypt(pwd, salt);
		List<String> tokenList = SecurityUtil.token(userId, salt);
		user.setSalt(StringUtil.string2Hex(salt));
		user.setPwd(StringUtil.string2Hex(pwd));
		user.setToken(StringUtil.string2Hex(tokenList.get(1)));
		user.setPwdstatus(null);
		
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
	
	@RequestMapping(value="/my", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> my(@RequestBody String jsonstring, 
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
		
		List<Map<String, Object>> statusCount = archiveService.statusCountByUserid(userId);
		
		long archdown = 0, archnodown = 0, archdelete = 0;
		for (Map<String, Object> countMap : statusCount) {
			long value = (Long) countMap.get("count(*)");
			switch ((Integer) countMap.get("status")) {
			case 0:
				archnodown = value;
				break;
			case 1:
				archdown = value;
				break;
			case -1:
				archdelete = value;
				break;
			}
		}
		
		List<String> adminnameList = inviteService.getAdminNameByInviteid(userId);
		if (adminnameList.size() > 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据错误！");
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("status", HttpStatus.OK.value());
		result.put("role", user.getRole());
		result.put("archcount", archdown + archnodown + archdelete);
		result.put("archdown", archdown);
		result.put("archnodown", archnodown);
		result.put("archdelete", archdelete);
		result.put("adminname", adminnameList.isEmpty() ? null : adminnameList.get(0));
		
		return result;
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
		
		List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
		int count = 0;
		
		if (Config.USE_INVITE) {
			if (user.getRole() < Config.ROLE_ADMIN) {
				return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
			}
			
			List<HashMap<String, Object>> resultHashMap = 
					inviteService.getUsersByCreateidAndStatus(userId, Config.STATUS_INVITE_USED);
			
			int idxStart = (page - 1) * Config.COUNT_PAGE_ITEM;
			int idxStop = idxStart + Config.COUNT_PAGE_ITEM;
			idxStop = idxStop > resultHashMap.size() ? resultHashMap.size() : idxStop;
			
			for (int i = idxStart; i < idxStop; i++) {
				HashMap<String, Object> jsonObject = new HashMap<String, Object>();
				HashMap<String, Object> r = resultHashMap.get(i);
				int archCount = archiveService.countByUserid((Integer) r.get("id"));
				jsonObject.put("id", (Integer) r.get("id"));
				jsonObject.put("name", (String) r.get("name"));
				jsonObject.put("role", (Integer) r.get("role"));
				jsonObject.put("archcount", archCount);
				jsonList.add(jsonObject);
			}
			count = resultHashMap.size();
			
		} else {
			if (role == null) role = user.getRole() - 1;
			
			if (user.getRole() < Config.ROLE_SUPERADMIN || user.getRole() <= role || role < 0) {
				return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
			}
			
			List<User> users = userService.getUsersByRole(role);
			
			int idxStart = (page - 1) * Config.COUNT_PAGE_ITEM;
			int idxStop = idxStart + Config.COUNT_PAGE_ITEM;
			idxStop = idxStop > users.size() ? users.size() : idxStop;
			
			for (int i = idxStart; i < idxStop; i++) {
				HashMap<String, Object> jsonObject = new HashMap<String, Object>();
				User u = users.get(i);
				int archCount = archiveService.countByUserid(u.getId());
				jsonObject.put("id", u.getId());
				jsonObject.put("name", u.getName());
				jsonObject.put("role", u.getRole());
				jsonObject.put("archcount", archCount);
				jsonList.add(jsonObject);
			}
			count = users.size();
		}
		
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("status", HttpStatus.OK.value());
		jsonResult.put("count", count);
		jsonResult.put("list", jsonList);
		
		return jsonResult;
	}
}
