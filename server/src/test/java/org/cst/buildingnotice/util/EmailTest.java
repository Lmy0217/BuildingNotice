package org.cst.buildingnotice.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class EmailTest {

	@Test
	public void sendEmailTest() {
		EmailUtil.sendEmail("lmy0217@126.com", "test", "测试");
	}
}
