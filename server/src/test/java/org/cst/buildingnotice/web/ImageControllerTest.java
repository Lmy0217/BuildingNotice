//package org.cst.buildingnotice.web;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.alibaba.fastjson.JSONObject;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
//@WebAppConfiguration
//@Rollback(value=false)
//@Transactional(transactionManager = "transactionManager")
//public class ImageControllerTest {
//
//	@Autowired
//	private WebApplicationContext wac;
//	
//	private MockMvc mockMvc;
//	
//	@Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }
//	
//	//@Test
//	public void uploadTest() throws Exception {
//		
//		MockMultipartFile jpgfile = new MockMultipartFile("file", "filename.jpg", 
//				MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = 				
//				MockMvcRequestBuilders.multipart("/image/upload").file(jpgfile)
//				.param("depict", "depict")
//				.param("token", "333ba6f82bc54c5254b51969fb7d74eb9fb882965b251d87b1ba6f4025a3f1a2816d");
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void downloadTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("id", 6);
//		requestBodyJSON.put("token", "333ba6f82bc54c5254b51969fb7d74eb9fb882965b251d87b1ba6f4025a3f1a2816d");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/image/download")
//	    		.param("json", requestBodyJSON.toString());
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse();
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//}
