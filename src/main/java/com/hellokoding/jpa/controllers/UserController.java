package com.hellokoding.jpa.controllers;

import com.hellokoding.jpa.models.Profile;
import com.hellokoding.jpa.models.User;
import com.hellokoding.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.security.auth.login.LoginContext;

/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public String login( Model model){
        model.addAttribute("user", new User());
        return "userlogin";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, Model model){
        if(!userService.isValidUser(user)){
            return "redirect:/userlogin";
        }
        else {
            model.addAttribute(user);
            return "userindex";
        }
    }
}
