package org.cst.buildingnotice.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.deepoove.poi.XWPFTemplate;

public class TemplateUtil {
	
	public static String stringRender(String template, String data) {
		
		if (template == null) return "Template null!";
		
		List<Character> operator = new ArrayList<Character>();
		List<Integer> choose = new ArrayList<Integer>();
		List<String> separator = new ArrayList<String>();
		StringBuilder stringBuilder = new StringBuilder();
		
		boolean read = true;
		int chooseData = -1;
		int chooseDataIdx = 0;
		int layer = 0;
		int serial = 0;
		
		boolean separ = false;
		String separate = "";
		
		int dataIdx = data.indexOf(';');
		int dataWidth = 0;
		try {
			dataWidth = Integer.parseInt(data.substring(0, dataIdx++));
		} catch (Exception e) {
		}
		
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
					if (read && !separate.equals("")) {
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
					try {
						chooseData = Integer.parseInt(data.substring(dataIdx, 
								dataIdx + dataWidth));
					} catch (Exception e) {
						return "Data error!";
					}
					dataIdx += dataWidth;
					chooseDataIdx = 0;
					layer = 0;
					separate = "";
				}
				break;
			case ']':
				operator.remove(operator.size() - 1);
				if (read && !separate.equals("")) {
					stringBuilder.delete(stringBuilder.length() 
							- separate.length(), stringBuilder.length());
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
					if (read && !separate.equals("")) {
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
					separate += c;
				} else {
					if (read) {
						if (c == '$') stringBuilder.append(++serial + "、");
						else stringBuilder.append(c);
					}
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	public static boolean render(String templateFile, 
			HashMap<String, Object> data, String outFile) {
		
		FileOutputStream out = null;
		XWPFTemplate template = null;
		boolean flag = true; 
		
		try {
			template = XWPFTemplate.compile(templateFile).render(data);
			out = new FileOutputStream(outFile);
			template.write(out); 
			out.flush();
		} catch (Exception e1) {
			flag = false;
		} finally {
			try {
				out.close();
				template.close();
			} catch (IOException e2) {
				flag = false;
			}
		}
		
		return flag;
	}
	
	public static List<String> transform(Integer id, String body1, String body2, String body3) {
		
		List<String> out = new ArrayList<String>();
		
		StringBuilder sbody3 = new StringBuilder();
		StringBuilder sadvise = new StringBuilder();
		
		String var1 = null, var3 = null, var4 = null, var5 = null, var6 = null;
		Integer var2 = null;
		
		try {
			switch (id) {
			case 1:
				
				// body1
				out.add("");
				
				// body2
				out.add("2;" + body2);
				
				// body3
				sbody3.append("2;");
				sadvise.append("2;");
				
				//   sentence_1
				var1 = body3.substring(2, 4);
				if (var1.equals("00")) sbody3.append("01");
				else if (var1.equals("01")) sbody3.append("02");
				else if (var1.equals("02")) sbody3.append("04");
				
				//   choose
				var2 = Integer.parseInt(body3.substring(0, 2));
				sbody3.append(String.format("%02d", var2 % 16));
				sadvise.append(String.format("%02d", var2 % 16));
				
				//   sentence_2
				var3 = body3.substring(4, 8);
				if (!var3.equals("0000")) sbody3.append(var3.substring(2) + var3.substring(0, 2));
				
				//   sentence_3
				var4 = body3.substring(8, 12);
				if (!var4.equals("0000")) sbody3.append(var4.substring(2) + var4.substring(0, 2));
				
				//   sentence_4
				var5 = body3.substring(12, 16);
				if (!var5.equals("0000")) sbody3.append(var5);
				
				//   sentence_5
				var6 = body3.substring(16, 20);
				if (!var6.equals("0000")) {
					sbody3.append(var6);
					sadvise.append(var6.substring(2));
				}
				
				out.add(sbody3.toString());
				out.add(sadvise.toString());
				
				break;
				
			case 2:
				
				// body1
				out.add("");
				
				// body2
				out.add("");
				
				// body3
				sbody3.append("2;");
				sadvise.append("2;");
				
				//   sentence_1
				var1 = body3.substring(2, 4);
				if (var1.equals("00")) sbody3.append("01");
				else if (var1.equals("01")) sbody3.append("02");
				else if (var1.equals("02")) sbody3.append("04");
						
				//   choose
				var2 = Integer.parseInt(body3.substring(0, 2));
				sbody3.append(String.format("%02d", var2 % 16));
				sadvise.append(String.format("%02d", var2 % 16));
				
				//   sentence_2
				var3 = body3.substring(4, 6);
				if (!var3.equals("00")) sbody3.append(var3);
				
				//   sentence_3
				var4 = body3.substring(6, 10);
				if (!var4.equals("0000")) {
					sbody3.append(var4.substring(2) + var4.substring(0, 2));
					sadvise.append(var4.substring(2));
				}
				
				//   sentence_4
				var5 = body3.substring(10, 12);
				if (!var5.equals("0000")) sbody3.append(var5);
				
				out.add(sbody3.toString());
				out.add(sadvise.toString());
				
				break;
				
			case 3:
				
				// body1
				out.add("");
				
				// body2
				out.add("2;" + body2);
				
				// body3
				sbody3.append("2;");
				sadvise.append("2;");
				
				//   sentence_1
				var1 = body3.substring(2, 4);
				if (var1.equals("00")) sbody3.append("01");
				else if (var1.equals("01")) sbody3.append("02");
				else if (var1.equals("02")) sbody3.append("04");
						
				//   choose
				var2 = Integer.parseInt(body3.substring(0, 2));
				sbody3.append(String.format("%02d", var2 % 16));
				sadvise.append(String.format("%02d", var2 % 16));
				
				//   sentence_2
				var3 = body3.substring(4, 8);
				if (!var3.equals("0000")) {
					sbody3.append(var3.substring(2) + var3.substring(0, 2));
					sadvise.append(var3.substring(2));
				}
				
				//   sentence_3
				var4 = body3.substring(8, 12);
				if (!var4.equals("0000")) {
					sbody3.append(var4.substring(2) + var4.substring(0, 2));
					sadvise.append(var4.substring(2));
				}
				
				//   sentence_4
				var5 = body3.substring(12, 16);
				if (!var5.equals("0000")) sbody3.append(var5);
				
				out.add(sbody3.toString());
				out.add(sadvise.toString());
				
				break;
				
			case 4:
				
				// body1
				out.add("");
				
				// body2
				out.add("2;" + body2);
				
				// body3
				sbody3.append("2;");
				sadvise.append("2;");
				
				//   sentence_1
				var1 = body3.substring(2, 4);
				if (var1.equals("00")) sbody3.append("01");
				else if (var1.equals("01")) sbody3.append("02");
				else if (var1.equals("02")) sbody3.append("04");
						
				//   choose
				var2 = Integer.parseInt(body3.substring(0, 2));
				sbody3.append(String.format("%02d", var2 % 16));
				sadvise.append(String.format("%02d", var2 % 16));
				
				//   sentence_2
				var3 = body3.substring(4, 6);
				if (!var3.equals("00")) sbody3.append(var3);
				
				//   sentence_3
				var4 = body3.substring(8, 12);
				if (!var4.equals("0000")) {
					sbody3.append(var4.substring(2) + var4.substring(0, 2));
					sadvise.append(var4.substring(2));
				}
				
				//   sentence_4
				var5 = body3.substring(12, 16);
				if (!var5.equals("0000")) sbody3.append(var5);
				
				out.add(sbody3.toString());
				out.add(sadvise.toString());
				
				break;
				
			case 5:
				
				// body1
				out.add("");
				
				// body2
				out.add("2;" + body2);
				
				// body3
				sbody3.append("2;");
				sadvise.append("2;");
				
				//   sentence_1
				var1 = body3.substring(2, 4);
				if (var1.equals("00")) sbody3.append("01");
				else if (var1.equals("01")) sbody3.append("02");
				else if (var1.equals("02")) sbody3.append("04");
				
				//   choose
				var2 = Integer.parseInt(body3.substring(0, 2));
				sbody3.append(String.format("%02d", var2 % 16));
				sadvise.append(String.format("%02d", var2 % 16));
				
				//   sentence_2
				var3 = body3.substring(4, 8);
				if (!var3.equals("0000")) sbody3.append(var3.substring(2) + var3.substring(0, 2));
				
				//   sentence_3
				var4 = body3.substring(8, 12);
				if (!var4.equals("0000")) sbody3.append(var4.substring(2) + var4.substring(0, 2));
				
				//   sentence_4
				var5 = body3.substring(12, 16);
				if (!var5.equals("0000")) sbody3.append(var5);
				
				//   sentence_5
				var6 = body3.substring(16, 20);
				if (!var6.equals("0000")) {
					sbody3.append(var6);
					sadvise.append(var6.substring(2));
				}
				
				out.add(sbody3.toString());
				out.add(sadvise.toString());
				
				break;
			}
		} catch (Exception e) {
			return null;
		}
		
		return out;
	}
}
