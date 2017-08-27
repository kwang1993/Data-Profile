package tk.kaicheng.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.kaicheng.POJO.FeatureAndValue;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.services.EntryService;
import tk.kaicheng.services.FeatureService;
import tk.kaicheng.services.ProfileService;
import tk.kaicheng.services.UserService;

import java.util.List;


/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private EntryService entryService;

    // get user from security context
    private User getContextUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }

    @RequestMapping(value = "/user/profiles", method = RequestMethod.GET)
    public String list(Model model) {
        User user = getContextUser();
        // get profiles of the user
        List<Profile> profiles = profileService.findByUser(user);
        model.addAttribute("profiles", profiles);
        return "/user/profiles";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/view", method = RequestMethod.GET)
    public String showProfile(@PathVariable Integer profileId, Model model) {
        User user = getContextUser();
        // view profile belonging to the user
        Profile profile = profileService.findOne(profileId);
        if (profile == null || profile.getUser() != user) return "redirect:/403";

        model.addAttribute("profile", profile);
        List<Feature> features = featureService.findByProfile(profile);
        model.addAttribute("features", features);

        List<Entry> entries = entryService.findByProfile(profile);
        model.addAttribute("entries", entries);
        return "/user/profileView";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer profileId, Model model) {
        User user = getContextUser();
        // edit profile belonging to the user
        Profile profile = profileService.findOne(profileId);
        if (profile == null || profile.getUser() != user) return "redirect:/403";

        model.addAttribute("profile", profile);
        return "/user/profileEdit";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable Integer profileId, Model model) {
        User user = getContextUser();
        // delete profile belonging to the user
        Profile profile = profileService.findOne(profileId);
        if (profile == null || profile.getUser() != user) return "redirect:/403";

        profileService.delete(profileId);
        return "redirect:/user/profiles";
    }

    @RequestMapping(value = "/user/profiles/new", method = RequestMethod.GET)
    public String newProfile(Model model) {
        // new profile
        model.addAttribute("profile", new Profile());
        return "/user/profileEdit";
    }

    @RequestMapping(value = "/user/profiles/saveOrUpdate", method = RequestMethod.POST)
    public String saveProfile(Profile profile) {
        // post profile
        User user = getContextUser();
        profile.setUser(user);
        Profile fetchedProfile = profileService.findByProfileNameAndUser(profile.getProfileName(), user);
        if (fetchedProfile == null) {
            profileService.save(profile);
        } else
            profileService.updateProfileName(profile.getId(), profile.getProfileName());
        return "redirect:/user/profiles";
    }
}