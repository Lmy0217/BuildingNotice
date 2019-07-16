package org.cst.buildingnotice.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class FileUtil {

	public static String getRealPath(HttpServletRequest request, String path) {
		
		String realPath = request.getServletContext().getRealPath(path);
		System.out.println("realPath :" + realPath);
		if (realPath == null) return null;
		
		File realPathFile = new File(realPath);
		if (!realPathFile.exists()) realPathFile.mkdirs();
		
		return realPath;
	}
}
