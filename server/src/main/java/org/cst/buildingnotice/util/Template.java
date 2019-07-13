package org.cst.buildingnotice.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.deepoove.poi.XWPFTemplate;

public class Template {
	
	public static String stringRender(String template, String data) {
		
		List<Character> operator = new ArrayList<Character>();
		List<Integer> choose = new ArrayList<Integer>();
		List<Character> separator = new ArrayList<Character>();
		StringBuilder stringBuilder = new StringBuilder();
		
		boolean read = true;
		int chooseData = -1;
		int chooseDataIdx = 0;
		int layer = 0;
		
		boolean separ = false;
		char separate = '\u0000';
		
		int dataIdx = data.indexOf(';');
		int dataWidth = Integer.parseInt(data.substring(0, dataIdx++));
		
		for (int i = 0; i < template.length(); i++) {
			char c = template.charAt(i);
			switch (c) {
			case '{':
				operator.add(c);
				if (chooseData != -1) {
					if (layer == 0) {
						read = (chooseData & (1 << chooseDataIdx)) > 0;
						chooseDataIdx++;
					}
				}
				layer++;
				break;
			case '}':
				operator.remove(operator.size() - 1);
				layer--;
				if (layer == 0) {
					if (read && separate != '\u0000') {
						stringBuilder.append(separate);
					}
					read = true;
				}
				break;
			case '[':
				operator.add(c);
				if (chooseData != -1) {
					if (layer == 0) {
						read = (chooseData & (1 << chooseDataIdx)) > 0;
						chooseDataIdx++;
					}
				}
				layer++;
				if (read) {
					if (chooseData != -1) {
						choose.add(chooseData);
						choose.add(chooseDataIdx);
						choose.add(layer);
						separator.add(separate);
					}
					chooseData = Integer.parseInt(data.substring(dataIdx, 
							dataIdx + dataWidth));
					dataIdx += dataWidth;
					chooseDataIdx = 0;
					layer = 0;
					separate = '\u0000';
				}
				break;
			case ']':
				operator.remove(operator.size() - 1);
				if (read && separate != '\u0000') {
					stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				}
				if (read) {
					if (choose.size() > 0) {
						separate = separator.remove(separator.size() - 1);
						layer = choose.remove(choose.size() - 1);
						chooseDataIdx = choose.remove(choose.size() - 1);
						chooseData = choose.remove(choose.size() - 1);
					}
				}
				layer--;
				if (layer == 0) {
					if (read && separate != '\u0000') {
						stringBuilder.append(separate);
					}
					read = true;
				}
				break;
			case '(':
				separ = true;
				break;
			case ')':
				separ = false;
				break;
			default:
				if (separ) {
					separate = c;
				} else {
					if (read) stringBuilder.append(c);
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static void render(String templateFile, 
			HashMap<String, Object> data, String outFile) throws IOException {
		
		XWPFTemplate template = XWPFTemplate.compile(templateFile).render(data);
		FileOutputStream out = new FileOutputStream(outFile);
		template.write(out); 
		out.flush();
		out.close();
		template.close();
	}
}
