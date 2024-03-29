//package org.cst.buildingnotice.web;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
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
//public class InviteControllerTest {
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
//	public void createTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "333b03b98ba31dc51f5f99a272e8080329dfc2fb53b4246a247d3aa8d617f62b1055");
//		requestBodyJSON.put("count", 10);
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/invite/create")
//	    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void listTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "333b50923ddff41c9d29392e24cc4720c5cb6610f52e393d9cffb5b29e39648bb89c");
//		requestBodyJSON.put("type", 1);
//		requestBodyJSON.put("page", 2);
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/invite/list")
//	    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//}
