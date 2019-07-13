package org.cst.buildingnotice.util;

import org.junit.Assert;
import org.junit.Test;

public class TemplateTest {

	@Test
	public void simpleRenderTest() {
		String temp = 
				"{[(,){{a}[{b1}{b2}]{c}[(,){d1}{d2}{d3}{d4}]{e}[(,){f1}{f2}]{g}[{h}]{i}}" + 
				"{{j}[(,){k1}{k2}{k3}]{l}[{m1}{m2}]{n}[{o}]}{p}]}";
		String data = null, out = null;
		
		data = "1;11111";
		out = Template.stringRender(temp, data);
		System.out.println(out);
		Assert.assertTrue(out.equals("ab1cd1ef1ghi"));
		
		data = "1;11211";
		out = Template.stringRender(temp, data);
		System.out.println(out);
		Assert.assertTrue(out.equals("ab1cd2ef1ghi"));
		
		data = "1;11311";
		out = Template.stringRender(temp, data);
		System.out.println(out);
		Assert.assertTrue(out.equals("ab1cd1,d2ef1ghi"));
		
		data = "1;2321";
		out = Template.stringRender(temp, data);
		System.out.println(out);
		Assert.assertTrue(out.equals("jk1,k2lm2no"));
		
		data = "1;4";
		out = Template.stringRender(temp, data);
		System.out.println(out);
		Assert.assertTrue(out.equals("p"));
		
		data = "2;0301100301060100";
		out = Template.stringRender(temp, data);
		System.out.println(out);
		Assert.assertTrue(out.equals("ab1cd2,d4ef1,f2ghi,jk2,k3lm1n"));
	}
}
