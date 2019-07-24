//package org.cst.buildingnotice.util;
//
//import java.util.HashMap;
//
//import org.cst.buildingnotice.service.TypeService;
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
//		String temp = typeService.getTypeById(5).getBody3();
//		String data = null, out = null;
//		
//		data = "2;0114090105010104";
//		out = TemplateUtil.stringRender(temp, data);
//		
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("body3", out);
//
//		String templateFile = "./src/main/webapp/uploads/template/template_0.docx";
//		String outFile = "out.docx";
//		TemplateUtil.render(templateFile, hashMap, outFile);
//	}
//}
