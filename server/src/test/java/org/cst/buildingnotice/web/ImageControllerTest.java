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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
@WebAppConfiguration
@Rollback(value=true)
@Transactional(transactionManager = "transactionManager")
public class ImageControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
	public void uploadTest() throws Exception {
		
		MockMultipartFile jpgfile = new MockMultipartFile("file", "filename.jpg", 
				MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = 				
				MockMvcRequestBuilders.multipart("/image/upload")
				.file(jpgfile).param("depict", "depict");
		
		ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
	}
}
