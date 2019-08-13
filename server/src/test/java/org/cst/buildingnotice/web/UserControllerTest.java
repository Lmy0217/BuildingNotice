//package org.cst.buildingnotice.web;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.servlet.http.Cookie;
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
//		requestBodyJSON.put("pwd", "abc123456");
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
//	public void emailsendTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("token", "333bad02731ad7a30d3e8a9b1f90a8c5603f495cba535ec106afd501da06b3956eb0");
//		requestBodyJSON.put("email", "lmy0217@126.com");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/email/send")
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void emailverifyTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("code", "333ba2de843e458d2ba65f5fdac989c310936486ac985c3ad20cfb35d543f180850a");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/email/verify")
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void resetsendTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("name", "myluo");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/reset/send")
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void resetverifyTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("code", "333bb42037aad2f57cae1a3a4ae457c9183e7601e6746b8e89571afedd64f36fe653");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/reset/verify")
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//		
//		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void resetactionTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		requestBodyJSON.put("code", "333b6bb93fac1a4be4a07ab5f0912e18ee407e69b43dbef3d83ab7118dc8b3712084");
//		requestBodyJSON.put("pwd", "abc123456");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/reset/action")
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
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
//		requestBodyJSON.put("token", "333bdf43624231102c1a32cbeb620684f2dcc7610a751105300b79ade105c98bfbd8");
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
