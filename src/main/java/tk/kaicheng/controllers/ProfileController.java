package tk.kaicheng.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.kaicheng.models.Profile;
import tk.kaicheng.services.ProfileService;


/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = "/profiles", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("profiles", profileService.findAll());
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

    @RequestMapping("profile/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        profileService.delete(id);
        return "redirect:/profiles";
    }

    @RequestMapping("profile/new")
    public String newProfile(Model model){
        model.addAttribute("profile", new Profile());
        return "profileform";
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String saveProfile(Profile profile) {
        profileService.save(profile);
        return "redirect:/profile/" + profile.getId();
    }

}