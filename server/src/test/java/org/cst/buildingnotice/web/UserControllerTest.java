//package org.cst.buildingnotice.web;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
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
//public class UserControllerTest {
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
//		HashMap<String, String> pwdMap = new HashMap<String, String>() {
//			private static final long serialVersionUID = 1L;
//			{
//				put("admin", "admin123456");
//				put("test", "a12345678");
//				put("myluo", "a12345678");
//			}
//		};
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		
//		for (HashMap.Entry<String, String> entry : pwdMap.entrySet()) {
//			
//			requestBodyJSON.put("name", entry.getKey());
//			requestBodyJSON.put("pwd", entry.getValue());
//			
//			MockHttpServletRequestBuilder mockHttpServletRequestBuilder = 
//					MockMvcRequestBuilders.post("/user/create")
//		    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//		    		.content(requestBodyJSON.toJSONString())
//		            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//			
//			ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//	        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//	        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//	        resultActions.andDo(MockMvcResultHandlers.print());
//		}
//	}
//	
//	//@Test
//	public void loginTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("name", "myluo");
//		requestBodyJSON.put("pwd", "a12345678");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/login")
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
//	public void logoutTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "363bd32e84dc8f37430081939592a31ad0ed86ae1102ab8506b6e99a8acd2e8ec5b2");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/logout")
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
//	public void tokenTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "363b9e2d422bbd64d95b64c55cd06b964ace883f608a994f7edbcada28c0e9f221d4");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/token")
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
//	public void roleTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "363bd32e84dc8f37430081939592a31ad0ed86ae1102ab8506b6e99a8acd2e8ec5b2");
//		requestBodyJSON.put("userid", 2);
//		requestBodyJSON.put("role", 5);
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/role")
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
//	public void myTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "383b9123b076a470d6da5ce25bdac62f6b80521180c28530f716334bfc0de8cad3a6");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/my")
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
//		requestBodyJSON.put("token", "333b58e3db364da6666cf6b3d974a4d070d0b4aac3815008e5236153ff05e4ad31c0");
//		requestBodyJSON.put("page", 1);
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/list")
//	    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//}
