package org.cst.buildingnotice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.entity.Image;
import org.cst.buildingnotice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/image")
@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/upload", produces={"application/json; charset=UTF-8"}, method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("depict") String depict, 
			HttpServletRequest request, Model model) throws IOException {
		
		String uploads_path = request.getServletContext().getRealPath("/uploads/images");
		System.out.println("realPath :" + uploads_path);
		
		File uploads_path_file = new File(uploads_path);
		if (!uploads_path_file.exists()) {
			System.out.println(uploads_path_file.mkdirs());
		}
		
		String file_name = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
		String file_path = uploads_path + File.separator + file_name;

		InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(new File(file_path));

		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
			out.flush();
		}

		out.close();
		in.close();
		
		int imageId = imageService.create(file_name, depict);
		
		Map<String, Object> dict = new HashMap<String, Object>();
		dict.put("status", HttpStatus.OK.value());
		dict.put("imageId", imageId);

		return dict;
	}

	@RequestMapping(value = "/download", method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(@RequestParam("id") int id, HttpServletRequest request, 
			Model model) throws Exception {
		
		Image image = imageService.getImageById(id);
		
		String uploads_path = request.getServletContext().getRealPath("/uploads/images");
		String file_path = uploads_path + File.separator + image.getPath();
		System.out.println("realPath :" + file_path);
		
		InputStream in = new FileInputStream(new File(file_path));
		
		byte[] body = new byte[in.available()];
		in.read(body);

		file_path = new String(file_path.getBytes("gbk"), "iso8859-1");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=" + image.getPath());
		
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
		
		in.close();
		
		return response;
	}
}
