package org.cst.buildingnotice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.config.Config;
import org.cst.buildingnotice.entity.Invite;
import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.InviteService;
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

@RequestMapping("/invite")
@Controller
public class InviteController {

	@Autowired
	private InviteService inviteService;
	
	@Autowired
    private UserService userService;
	
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
		
		String hexToken = json.getString("token");
		Integer count = json.getInteger("count");
		if (hexToken == null || count == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "缺少必要参数！");
		}
		if (count < 1 || count > Config.LIMIT_MAX_CREATE_INVITE) {
			return ExceptionUtil.getMsgMap(HttpStatus.BAD_REQUEST, "个数范围 1-" + Config.LIMIT_MAX_CREATE_INVITE + "！");
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
		
		if (user.getRole() < Config.ROLE_ADMIN) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		int flag = inviteService.create(userId, count);
		if (flag != count) {
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
		
		if (user.getRole() < Config.ROLE_ADMIN) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		List<Invite> invites = inviteService.getInvitesByCreateid(userId);
		if (type == Config.STATUS_INVITE_NOUSE + 1 || type == Config.STATUS_INVITE_USED + 1) {
			for (int i = invites.size() - 1; i >= 0; i--) {
				if (invites.get(i).getStatus() + 1 != type) {
					invites.remove(i);
				}
			}
		}
		int idxStart = (page - 1) * Config.COUNT_PAGE_ITEM;
		int idxStop = idxStart + Config.COUNT_PAGE_ITEM;
		idxStop = idxStop > invites.size() ? invites.size() : idxStop;
		
		List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
		for (int i = idxStart; i < idxStop; i++) {
			HashMap<String, Object> jsonObject = new HashMap<String, Object>();
			Invite invite = invites.get(i);
			jsonObject.put("code", invite.getCode());
			User inviteUser = null;
			if (invite.getInviteid() != null) {
				inviteUser = userService.getUserById(invite.getInviteid());
			}
			jsonObject.put("invite", inviteUser != null ? inviteUser.getName() : null);
			jsonObject.put("status", invite.getStatus());
			jsonList.add(jsonObject);
		}
		
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("status", HttpStatus.OK.value());
		jsonResult.put("count", invites.size());
		jsonResult.put("list", jsonList);
		
		return jsonResult;
	}
}
