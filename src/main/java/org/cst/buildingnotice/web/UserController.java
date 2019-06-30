package org.cst.buildingnotice.web;

import javax.servlet.http.HttpServletRequest;

import org.cst.buildingnotice.entity.User;
import org.cst.buildingnotice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@RequestMapping(value="/showname", produces={"application/json; charset=UTF-8"}, method=RequestMethod.GET)
	@ResponseBody
    public String showUserName(@RequestParam("uid") int uid, HttpServletRequest request, Model model){
        System.out.println("showname");
        User user = userService.getUserById(uid);
        if(user != null){
            request.setAttribute("name", user.getName());
            model.addAttribute("name", user.getName());
            return "showName";
        }
        request.setAttribute("error", "没有找到该用户！");
        return "error";
    }
}
