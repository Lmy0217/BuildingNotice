package org.cst.buildingnotice.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
@Rollback(value=false)
@Transactional(transactionManager = "transactionManager")
public class ImageControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	//@Test
	public void uploadTest() throws Exception {
		
		MockMultipartFile jpgfile = new MockMultipartFile("file", "filename.jpg", 
				MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = 				
				MockMvcRequestBuilders.multipart("/image/upload").file(jpgfile)
				.param("depict", "depict")
				.param("token", "363b736e3206180a4265e4becb0ac4caba706053e8a6384ee226e60c77c7a1fd48e7");
		
		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
	}
	
	//@Test
	public void downloadTest() throws Exception {
		
		JSONObject requestBodyJSON = new JSONObject();
		requestBodyJSON.put("id", 245);
		requestBodyJSON.put("token", "363b736e3206180a4265e4becb0ac4caba706053e8a6384ee226e60c77c7a1fd48e7");
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/image/download")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBodyJSON.toJSONString())
	            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        resultActions.andReturn().getResponse();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
	}
}
