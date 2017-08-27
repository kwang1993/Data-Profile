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
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.services.FeatureService;
import tk.kaicheng.services.FeatureService;
import tk.kaicheng.services.ProfileService;
import tk.kaicheng.services.UserService;

import java.util.List;


/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class FeatureController {
    @Autowired
    private FeatureService featureService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    private Profile getContextProfile(Integer profileId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
<<<<<<< HEAD
        return user;
=======
        Profile profile = profileService.findOne(profileId);
        if (profile.getUser() != user) return null;
        return profile;
>>>>>>> 42dc2d8a50a7ac759bfbb3fc2a93d4179166fd74
    }

    @RequestMapping(value = "/user/profiles/{profileId}/features", method = RequestMethod.GET)
    public String list(@PathVariable Integer profileId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";

        // get features of the profile
        List<Feature> features = featureService.findByProfile(profile);
        model.addAttribute("profile", profile);
        model.addAttribute("features", features);
        return "/user/features";
    }



    @RequestMapping(value = "/user/profiles/{profileId}/features/{featureId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer profileId, @PathVariable Integer featureId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // edit feature belonging to the user
        Feature feature = featureService.findOne(featureId);
        if(feature == null || feature.getProfile() != profile) return "redirect:/403";
        model.addAttribute("profile", profile);
        model.addAttribute("feature", feature);
        return "/user/featureEdit";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/features/{featureId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable Integer profileId, @PathVariable Integer featureId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // delete feature belonging to the user
        Feature feature = featureService.findOne(featureId);
        if(feature == null || feature.getProfile() != profile) return "redirect:/403";

        featureService.delete(featureId);
        return "redirect:/user/profiles/" + profileId + "/features";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/features/new", method = RequestMethod.GET)
    public String newFeature(@PathVariable Integer profileId, Model model){

        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // new feature
        model.addAttribute("profile", profile);
        model.addAttribute("feature", new Feature());
        return "/user/featureEdit";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/features/saveOrUpdate", method = RequestMethod.POST)
    public String saveFeature(@PathVariable Integer profileId, Feature feature) {
        // post feature
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        feature.setProfile(profile);
        Feature fetchedFeature = featureService.findByFeatureNameAndProfile(feature.getFeatureName(), profile);
        if( fetchedFeature == null) {
            featureService.save(feature);
        }
        else
            featureService.updateFeatureName(feature.getId(), feature.getFeatureName());
        return "redirect:/user/profiles/" + profileId + "/features";
    }

}