package org.cst.buildingnotice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.entity.Image;
import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.ImageService;
import org.cst.buildingnotice.service.UserService;
import org.cst.buildingnotice.util.ExceptionUtil;
import org.cst.buildingnotice.util.FileUtil;
import org.cst.buildingnotice.util.SecurityUtil;
import org.cst.buildingnotice.util.StringUtil;
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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@RequestMapping("/image")
@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/upload", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(
			@RequestParam(value="file", required=false) MultipartFile file, 
			@RequestParam(value="depict", required=false) String depict, 
			@RequestParam(value="token", required=false) String hexToken, 
			HttpServletRequest request, Model model) {
		
		if (file == null || depict == null || hexToken == null) {
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
		
		if (user.getRole() < 1) {
			return ExceptionUtil.getMsgMap(HttpStatus.FORBIDDEN, "权限禁止！");
		}
		
		String file_name = UUID.randomUUID().toString().replace("-", "") 
				+ "_" + file.getOriginalFilename();
		String uploads_path = FileUtil.getRealPath(request, "/uploads/images");
		String file_path = uploads_path + File.separator + file_name;

		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			in = file.getInputStream();
			out = new FileOutputStream(new File(file_path));
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "文件读写错误！");
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch (IOException e) {
				return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "文件读写错误！");
			}
		}		
		
		Integer imageId = imageService.create(file_name, depict);
		if (imageId == null) {
			return ExceptionUtil.getMsgMap(HttpStatus.INTERNAL_SERVER_ERROR, "数据库错误！");
		}

		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", HttpStatus.OK.value());
				put("imageid", imageId);
			}
		};
	}

	// TODO test download type
	@RequestMapping(value = "/download", method=RequestMethod.POST)
	public ResponseEntity<byte[]> download(@RequestBody String jsonstring, 
			HttpServletRequest request, Model model) {
		
		System.out.println(jsonstring);
		
		JSONObject json = null;
		try {
			json = JSONObject.parseObject(jsonstring);
		} catch (JSONException e) {
			return null;
		}
		
		Integer id = json.getInteger("id");
		String hexToken = json.getString("token");
		if (id == null || hexToken == null) {
			return null;
		}
		
		String token = StringUtil.hex2String(hexToken);
		Integer userId = SecurityUtil.getIdInToken(token);
		if (userId == -1) {
			return null;
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			return null;
		}
		
		if (user.getToken() == null) {
			return null;
		}
		Boolean verifyFlag = SecurityUtil.verifyToken(token, StringUtil.hex2String(user.getToken()));
		if (!verifyFlag) {
			return null;
		}
		
		if (user.getRole() < 1) {
			return null;
		}
		
		Image image = imageService.getImageById(id);
		if (image == null) {
			return null;
		}
		
		String uploads_path = FileUtil.getRealPath(request, "/uploads/images");
		String file_path = uploads_path + File.separator + image.getPath();
		try {
			file_path = new String(file_path.getBytes("gbk"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		
		InputStream in = null;;
		byte[] body = null;
		try {
			in = new FileInputStream(new File(file_path));
			body = new byte[in.available()];
			in.read(body);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				return null;
			}
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=" + image.getPath());
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
		
		return response;
	}
}
