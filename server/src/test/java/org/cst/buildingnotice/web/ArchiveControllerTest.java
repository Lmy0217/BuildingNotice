package org.cst.buildingnotice.web;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
@WebAppConfiguration
@Rollback(value=true)
@Transactional(transactionManager = "transactionManager")
public class ArchiveControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
	public void createTest() throws Exception {
		
		JSONObject requestBodyJSON = new JSONObject();
		requestBodyJSON.put("unit", "江西省南昌市");
		requestBodyJSON.put("phone", "19568725565");
		requestBodyJSON.put("material", "无");
		requestBodyJSON.put("addr", "新建县学府大道999号");
		requestBodyJSON.put("hold", "户主名称");
		requestBodyJSON.put("holdid", "36010518450296756x");
		requestBodyJSON.put("attr", "住宅");
		requestBodyJSON.put("layer", 1);
		requestBodyJSON.put("createyear", "1995-02-07");
		requestBodyJSON.put("typeid", 2);
		requestBodyJSON.put("body2", "2;150101");
		requestBodyJSON.put("body3", "2;151501150101070103");
		requestBodyJSON.put("remark", "砖木结构");
		
		List<Integer> imgs = Arrays.asList(1, 2);
		requestBodyJSON.put("imgs", imgs);
		List<Integer> damage = Arrays.asList(10, 5, 9, 2, 7, 0, 16, 2, 13, 1, 23, 2, 5, 2, 7, 1, 6, 1);
		requestBodyJSON.put("damage", damage);
		
//	    mockMvc.perform(MockMvcRequestBuilders.post("/archive/create")
//	            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
//	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) //执行请求
//	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)) //验证响应contentType
//	            .andExpect(MockMvcResultMatchers.jsonPath("$.archid").value(1));
	    
	    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/archive/create")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
	    
	    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
	    
//	    String errorBody = "{id:1, name:zhang}";
//	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/archive/create")
//	            .contentType(MediaType.APPLICATION_JSON).content(errorBody)
//	            .accept(MediaType.APPLICATION_JSON)) //执行请求
//	            .andExpect(MockMvcResultMatchers.status().isBadRequest()) //400错误请求
//	            .andReturn();
//	    
//	    Assert.assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException().getClass()));//错误的请求内容体
	}
}
