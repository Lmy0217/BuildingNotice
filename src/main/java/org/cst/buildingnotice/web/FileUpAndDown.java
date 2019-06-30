package org.cst.buildingnotice.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUpAndDown {

//	public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
//        ServletContext sc = request.getSession().getServletContext();
//        String dir = sc.getRealPath("/upload");
//        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        Random r = new Random();
//        String imgName = "";
//        if ("jpg".equals(type)) {
//            imgName = sdf.format(new Date()) + r.nextInt(100) + ".jpg";
//        } else if ("png".equals(type)) {
//            imgName = sdf.format(new Date()) + r.nextInt(100) + ".png";
//        } else if ("jpeg".equals(type)) {
//            imgName = sdf.format(new Date()) + r.nextInt(100) + ".jpeg";
//        } else if ("gif".equals(type)) {
//            imgName = sdf.format(new Date()) + r.nextInt(100) + ".gif";
//        } else {
//            return null;
//        }
//        try {
//            FileUtils.writeByteArrayToFile(new File(dir, imgName), file.getBytes());
//        } catch (IOException e) {
//        	
//        }
//        return Result.ok().put("url", "/upload/" + imgName);
//    }
}
