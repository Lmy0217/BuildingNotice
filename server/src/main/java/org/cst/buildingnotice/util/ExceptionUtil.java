package org.cst.buildingnotice.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ExceptionUtil {

	public static Map<String, Object> getMsgMap(HttpStatus status, String msg) {
		
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("status", status.value());
				put("msg", msg);
			}
		};
	}
}
