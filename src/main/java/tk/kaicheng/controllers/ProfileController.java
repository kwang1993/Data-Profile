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

    @RequestMapping(value = "/user/profiles", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("profiles", profileService.findAll());
        return "/user/profiles";
    }

    @RequestMapping(value = "/user/profile/{id}", method = RequestMethod.GET)
    public String showProfile(@PathVariable Integer id, Model model){
        model.addAttribute("profile", profileService.findOne(id));
        return "/user/profileshow";
    }

    @RequestMapping(value = "/user/profile/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("profile", profileService.findOne(id));
        return "/user/profileform";
    }

    @RequestMapping(value = "/user/profile/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, Model model){
        profileService.delete(id);
        return "redirect:/user/profiles";
    }

    @RequestMapping(value = "/user/profile/new", method = RequestMethod.GET)
    public String newProfile(Model model){
        model.addAttribute("profile", new Profile());
        return "/user/profileform";
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String saveProfile(Profile profile) {
        if(profileService.findOne(profile.getId()) == null)
            profileService.save(profile);
        else
            profileService.updateProfileName(profile.getId(), profile.getProfileName());
        return "redirect:/user/profile/" + profile.getId();
    }

}
