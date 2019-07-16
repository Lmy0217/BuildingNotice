package org.cst.buildingnotice.config;

import java.util.HashMap;

import com.deepoove.poi.data.PictureRenderData;

public class Template {

	public static HashMap<String, Object> defaultValue(HashMap<String, Object> data) {
		
		String unit = (String) data.getOrDefault("unit", null);
		if (unit == null || unit.length() == 0) data.put("unit", "不详");
		
		String phone = (String) data.getOrDefault("phone", null);
		if (phone == null || phone.length() == 0) data.put("phone", "不详");
		
		String material = (String) data.getOrDefault("material", null);
		if (material == null || material.length() == 0) data.put("material", "无");
		
		String addr = (String) data.getOrDefault("addr", null);
		if (addr == null || addr.length() == 0) data.put("addr", "不详");
		
		String hold = (String) data.getOrDefault("hold", null);
		if (hold == null || hold.length() == 0) data.put("hold", "不详");
		
		String holdid = (String) data.getOrDefault("holdid", null);
		if (holdid == null || holdid.length() == 0) data.put("holdid", "不详");
		
		String attr = (String) data.getOrDefault("attr", null);
		if (attr == null || attr.length() == 0) data.put("attr", "不详");
		
		Integer layer = (Integer) data.getOrDefault("layer", null);
		if (layer == null || layer < 1) data.put("attr", Integer.valueOf(0));
		
		String createyear = (String) data.getOrDefault("createyear", null);
		if (createyear == null || createyear.length() == 0) data.put("createyear", "不详");
		
		String body1 = (String) data.getOrDefault("body1", null);
		if (body1 == null || body1.length() == 0) data.put("body1", "无");
		
		String body2 = (String) data.getOrDefault("body2", null);
		if (body2 == null || body2.length() == 0) data.put("body2", "无");
		
		String body3 = (String) data.getOrDefault("body3", null);
		if (body3 == null || body3.length() == 0) data.put("body3", "无");
		
		for (int j = 0; j < 4; j++) {
			PictureRenderData image = (PictureRenderData) data.getOrDefault("image" + j, null);
			String imagedepict = (String) data.getOrDefault("imagedepict" + j, null);
			if (image == null || image.getPath() == null || image.getPath().length() == 0 
					|| imagedepict == null || imagedepict.length() == 0) {
				data.remove("image" + j);
				data.put("imagedepict" + j, "");
			}
		}
		
		Integer a111 = (Integer) data.getOrDefault("a111", null);
		if (a111 == null || a111 < 1) data.put("a111", Integer.valueOf(0));
		
		Integer a112 = (Integer) data.getOrDefault("a112", null);
		if (a112 == null || a112 < 1) data.put("a112", Integer.valueOf(0));
		
		Integer a121 = (Integer) data.getOrDefault("a121", null);
		if (a121 == null || a121 < 1) data.put("a121", Integer.valueOf(0));
		
		Integer a122 = (Integer) data.getOrDefault("a122", null);
		if (a122 == null || a122 < 1) data.put("a122", Integer.valueOf(0));
		
		Integer a131 = (Integer) data.getOrDefault("a131", null);
		if (a131 == null || a131 < 1) data.put("a131", Integer.valueOf(0));
		
		Integer a132 = (Integer) data.getOrDefault("a132", null);
		if (a132 == null || a132 < 1) data.put("a132", Integer.valueOf(0));
		
		Integer a211 = (Integer) data.getOrDefault("a211", null);
		if (a211 == null || a211 < 1) data.put("a211", Integer.valueOf(0));
		
		Integer a212 = (Integer) data.getOrDefault("a212", null);
		if (a212 == null || a212 < 1) data.put("a212", Integer.valueOf(0));
		
		Integer a221 = (Integer) data.getOrDefault("a221", null);
		if (a221 == null || a221 < 1) data.put("a221", Integer.valueOf(0));
		
		Integer a222 = (Integer) data.getOrDefault("a222", null);
		if (a222 == null || a222 < 1) data.put("a222", Integer.valueOf(0));
		
		Integer a311 = (Integer) data.getOrDefault("a311", null);
		if (a311 == null || a311 < 1) data.put("a311", Integer.valueOf(0));
		
		Integer a312 = (Integer) data.getOrDefault("a312", null);
		if (a312 == null || a312 < 1) data.put("a312", Integer.valueOf(0));
		
		Integer a411 = (Integer) data.getOrDefault("a411", null);
		if (a411 == null || a411 < 1) data.put("a411", Integer.valueOf(0));
		
		Integer a412 = (Integer) data.getOrDefault("a412", null);
		if (a412 == null || a412 < 1) data.put("a412", Integer.valueOf(0));
		
		Integer a511 = (Integer) data.getOrDefault("a511", null);
		if (a511 == null || a511 < 1) data.put("a511", Integer.valueOf(0));
		
		Integer a512 = (Integer) data.getOrDefault("a512", null);
		if (a512 == null || a512 < 1) data.put("a512", Integer.valueOf(0));
		
		Integer a611 = (Integer) data.getOrDefault("a611", null);
		if (a611 == null || a611 < 1) data.put("a611", Integer.valueOf(0));
		
		Integer a612 = (Integer) data.getOrDefault("a612", null);
		if (a612 == null || a612 < 1) data.put("a612", Integer.valueOf(0));
		
		Double rankratio = (Double) data.getOrDefault("rankratio", null);
		if (rankratio == null || rankratio < 0 || rankratio > 1) 
			data.put("rankratio", Double.valueOf(0));
		
		String rankdepict = (String) data.getOrDefault("rankdepict", null);
		if (rankdepict == null || rankdepict.length() == 0) data.put("rankdepict", "");
		
		String rankname = (String) data.getOrDefault("rankname", null);
		if (rankname == null || rankname.length() == 0) data.put("rankname", "");
		
		String advise = (String) data.getOrDefault("advise", null);
		if (advise == null || advise.length() == 0) data.put("advise", "无");
		
		return data;
	}
}
