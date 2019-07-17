//package org.cst.buildingnotice.web;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
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
//public class ArchiveControllerTest {
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
//		requestBodyJSON.put("token", "363b736e3206180a4265e4becb0ac4caba706053e8a6384ee226e60c77c7a1fd48e7");
//		requestBodyJSON.put("unit", "江西省南昌市");
//		requestBodyJSON.put("phone", "19568725565");
//		requestBodyJSON.put("material", "无");
//		requestBodyJSON.put("addr", "新建县学府大道999号");
//		requestBodyJSON.put("hold", "户主名称");
//		requestBodyJSON.put("holdid", "36010518450296756x");
//		requestBodyJSON.put("attr", "住宅");
//		requestBodyJSON.put("layer", 1);
//		requestBodyJSON.put("createyear", "1995-02-07");
//		requestBodyJSON.put("typeid", 2);
//		requestBodyJSON.put("body2", "2;150101");
//		requestBodyJSON.put("body3", "2;151501150101070103");
//		requestBodyJSON.put("remark", "砖木结构");
//		
//		List<Integer> imgs = Arrays.asList(1, 2);
//		requestBodyJSON.put("imgs", imgs);
//		List<Integer> damage = Arrays.asList(10, 5, 9, 2, 7, 0, 16, 2, 13, 1, 23, 2, 5, 2, 7, 1, 6, 1);
//		requestBodyJSON.put("damage", damage);
//	    
//	    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/archive/create")
//	    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
//	    
//	    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//	
//	//@Test
//	public void downloadTest() throws Exception {
//		
//		JSONObject requestBodyJSON = new JSONObject();
//		
//		List<Integer> ids = Arrays.asList(16, 17);
//		requestBodyJSON.put("ids", ids);
//		requestBodyJSON.put("token", "333ba6f82bc54c5254b51969fb7d74eb9fb882965b251d87b1ba6f4025a3f1a2816d");
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/archive/download")
//				.param("json", requestBodyJSON.toString());
//	    
//	    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//        resultActions.andReturn().getResponse();
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andDo(MockMvcResultHandlers.print());
//	}
//}
