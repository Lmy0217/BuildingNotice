package org.cst.buildingnotice.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
	
	public static ResponseEntity<byte[]> getMsgEntity(HttpStatus status, String msg) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return new ResponseEntity<byte[]>(StringUtil.string2Byte(msg), headers, status);
	}
}
