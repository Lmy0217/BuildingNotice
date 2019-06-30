package org.cst.buildingnotice.service;

import org.cst.buildingnotice.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest2 {
	
	public static void main(String[] args) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService uService = (UserService) application.getBean("userService");
        User user = uService.getUserById(1);
        System.out.println(user.getName());
    }
}
