package com.hellokoding.jpa.controllers;

import com.hellokoding.jpa.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = "/profiles", method = RequestMethod.GET)
    public String findAll(Model model){
        model.addAttribute("profiles", profileService.findAll());
        System.out.println("Returning profiles:");
        return "profiles";
    }
}
