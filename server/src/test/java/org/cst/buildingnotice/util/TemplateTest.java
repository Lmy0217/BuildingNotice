//package org.cst.buildingnotice.util;
//
//import java.util.regex.Pattern;
//
//import org.cst.buildingnotice.service.TypeService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:applicationContext.xml"})
//public class TemplateTest {
//	
//	@Autowired
//	private TypeService typeService;
//
//	@Test
//	public void simpleRenderTest() {
////		String temp = 
////				"[{1）基础工作基本正常，地基基础无明显不均匀沉降；}{1）基础工作不正常，地基基础轻微沉降；}{1）基础工作不正常，地基基础严重沉降；}]\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：\n    [(；\n    ){$[(、){木梁}{木柱}{木屋架}{木檩条}]存在[{轻微}{严重}]腐蚀现象}{$[(、){木梁}{木柱}{木屋架}{木檩条}]存在[{轻微}{严重}]开裂现象}{$柱脚有[{轻微}{严重}][{变形}{倾斜}{破损}]现象}{$屋面板存在[{轻微}{严重}][{破损}{渗水}]现象}]。";
////		temp = typeService.getTypeById(1).getBody3();
////		String data = null, out = null;
////		
////		data = "2;010312010102";
////		out = TemplateUtil.stringRender(temp, data);
////		System.out.println(out);
//		
//		System.out.println(Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{2,15}$", "test"));
//		
//		
//		
//		//Assert.assertTrue(out.equals("ab1cd1ef1ghi"));
//		
////		data = "1;11211";
////		out = TemplateUtil.stringRender(temp, data);
////		System.out.println(out);
////		//Assert.assertTrue(out.equals("ab1cd2ef1ghi"));
////		
////		data = "1;11311";
////		out = TemplateUtil.stringRender(temp, data);
////		System.out.println(out);
////		//Assert.assertTrue(out.equals("ab1cd1,d2ef1ghi"));
////		
////		data = "1;2321";
////		out = TemplateUtil.stringRender(temp, data);
////		System.out.println(out);
////		//Assert.assertTrue(out.equals("jk1,k2lm2no"));
////		
////		data = "1;4";
////		out = TemplateUtil.stringRender(temp, data);
////		System.out.println(out);
////		//Assert.assertTrue(out.equals("p"));
////		
////		data = "2;0301100301060100";
////		out = TemplateUtil.stringRender(temp, data);
////		System.out.println(out);
////		//Assert.assertTrue(out.equals("ab1cd2,d4ef1,f2ghi,jk2,k3lm1n"));
//	}
//}
