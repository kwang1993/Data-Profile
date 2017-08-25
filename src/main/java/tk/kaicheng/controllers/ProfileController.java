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
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.ProfileFeature;
import tk.kaicheng.models.User;
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

    // get user from security context
    private User getContextUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(auth.getName());
        return user;
    }
    @RequestMapping(value = "/user/profiles", method = RequestMethod.GET)
    public String list(Model model){
        User user = getContextUser();
        // get profiles of the user
        List<Profile> profiles = profileService.findByUser(user);
        model.addAttribute("profiles", profiles);
        return "/user/profiles";
    }

    @RequestMapping(value = "/user/profile/{id}", method = RequestMethod.GET)
    public String showProfile(@PathVariable Integer id, Model model){
        User user = getContextUser();
        // view profile belonging to the user
        Profile profile = profileService.findOne(id);
        if(profile == null || profile.getUser() != user) return "redirect:/403";
        model.addAttribute("profile", profile);
        List<FeatureAndValue> featureAndValues = profileService.findFeatureAndValuesByProfileId(profile.getId());
        model.addAttribute("featureAndValues", featureAndValues);
        return "/user/profileshow";
    }

    @RequestMapping(value = "/user/profile/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){
        User user = getContextUser();
        // edit profile belonging to the user
        Profile profile = profileService.findOne(id);
        if(profile == null || profile.getUser() != user) return "redirect:/403";
        model.addAttribute("profile", profile);
        return "/user/profileform";
    }

    @RequestMapping(value = "/user/profile/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, Model model){
        User user = getContextUser();
        // delete profile belonging to the user
        Profile profile = profileService.findOne(id);
        if(profile == null || profile.getUser() != user) return "redirect:/403";
        profileService.delete(id);
        return "redirect:/user/profiles";
    }

    @RequestMapping(value = "/user/profile/new", method = RequestMethod.GET)
    public String newProfile(Model model){
        // new profile
        model.addAttribute("profile", new Profile());
        return "/user/profileform";
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String saveProfile(Profile profile) {
        // post profile
        User user = getContextUser();
        profile.setUser(user);
        Profile fetchedProfile = profileService.findOne(profile.getId());
        if( fetchedProfile == null) {
            profileService.save(profile);
        }
        else
            profileService.updateProfileName(profile.getId(), profile.getProfileName());
        return "redirect:/user/profile/" + profile.getId();
    }


    // profile-feature pairs
    @RequestMapping(value = "/user/profile/{id}/edit/{featureId}", method = RequestMethod.GET)
    public String editFeature(@PathVariable Integer id, @PathVariable Integer featureId, Model model){
        User user = getContextUser();
        // edit profile belonging to the user
        Profile profile = profileService.findOne(id);
        Feature feature = featureService.findOne(featureId);
        if(profile == null || feature == null || profile.getUser() != user || feature.getUser() != user) return "redirect:/403";

        FeatureAndValue featureAndValue = profileService.findFeatureAndValueById(profile.getId(), feature.getId());
        model.addAttribute("featureAndValue", featureAndValue);
        return "/user/profileFeatureForm";

    }

    @RequestMapping(value = "/user/profile/{id}/delete/{featureId}", method = RequestMethod.GET)
    public String deleteFeature(@PathVariable Integer id, @PathVariable Integer featureId, Model model){
        User user = getContextUser();
        // delete profile belonging to the user
        Profile profile = profileService.findOne(id);
        Feature feature = featureService.findOne(featureId);
        if(profile == null || feature == null || profile.getUser() != user || feature.getUser() != user) return "redirect:/403";
        if(profileService.findProfileFeatureById(id, featureId) != null)
            profileService.deleteProfileFeatureById(id, featureId);
        return "redirect:/user/profile/" + id;
    }

    @RequestMapping(value = "/user/profile/{id}/feature/new", method = RequestMethod.GET)
    public String newProfileFeature(@PathVariable Integer id,  Model model){
        User user = getContextUser();
        // delete profile belonging to the user
        Profile profile = profileService.findOne(id);
        if(profile == null || profile.getUser() != user) return "redirect:/403";

        FeatureAndValue featureAndValue = new FeatureAndValue();
        featureAndValue.setProfileId(id);
        model.addAttribute("featureAndValue", featureAndValue);
        return "/user/profileFeatureForm";
    }

    @RequestMapping(value = "/user/profile/{id}/feature", method = RequestMethod.POST)
    public String saveProfileFeature(@PathVariable Integer id, FeatureAndValue featureAndValue) {
        User user = getContextUser();

        // edit profile belonging to the user
        int profileId = id;
        Profile profile = profileService.findOne(profileId);
        if(profile == null || profile.getUser() != user ) return "redirect:/403";

        String featureName = featureAndValue.getFeatureName();
        Feature feature = featureService.findByFeatureNameAndUser(featureName, user);
        if(feature == null){
            feature = new Feature();
            feature.setFeatureName("featureName");
            feature.setUser(user);
            featureService.save(feature);
        }

        int featureId = feature.getId();
        String featureValue = featureAndValue.getFeatureValue();
        if(profileService.findProfileFeatureById(profileId, featureId) == null)
            profileService.saveProfileFeature(profileId, featureId, featureValue);
        else
            profileService.updateProfileFeatureByFeatureValue(profileId, featureId, featureValue);
        return "redirect:/user/profile/" + profileId;
    }
}
