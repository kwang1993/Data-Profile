package com.hellokoding.jpa.controllers;

import com.hellokoding.jpa.models.Profile;
import com.hellokoding.jpa.services.ProfileService;
import com.hellokoding.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profiles", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("profiles", profileService.findAll());
        System.out.println("Returning profiles:");
        return "profiles";
    }

    @RequestMapping("profile/{id}")
    public String showProfile(@PathVariable Integer id, Model model){
        model.addAttribute("profile", profileService.findOne(id));
        return "profileshow";
    }

    @RequestMapping("profile/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("profile", profileService.findOne(id));
        return "profileform";
    }

    @RequestMapping("profile/new")
    public String newProfile(Model model){
        model.addAttribute("profile", new Profile());
        return "profileform";
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST) // Update is problematic since linked to profileFeature
    public String saveProfile(Profile profile) {

        profileService.save(profile);

        return "redirect:/profile/" + profile.getId();
    }

}
