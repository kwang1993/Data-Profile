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

/**
 * Created by wangkaicheng on 2017/8/26.
 */
@Controller
public class EntryFeatureController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private EntryService entryService;

    private Profile getContextProfile(Integer profileId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Profile profile = profileService.findOne(profileId);
        if (profile.getUser() != user) return null;
        return profile;
    }
//    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/features", method = RequestMethod.GET)
//    public String entryFeatures(@PathVariable Integer profileId, @PathVariable Integer entryId, @PathVariable Integer featureId, Model model) {
//        Profile profile = getContextProfile(profileId);
//        if (profile == null) return "redirect:/403";
//
//        Entry entry = entryService.findOne(entryId);
//        if (entry == null || entry.getProfile() != profile) return "redirect:/403";
//
//        model.addAttribute("entry", entry);
//        model.addAttribute("")
//        return "/user/entryFeatures";
//    }
    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/features/{featureId}/edit", method = RequestMethod.GET)
    public String editFeature(@PathVariable Integer profileId, @PathVariable Integer entryId, @PathVariable Integer featureId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";

        Entry entry = entryService.findOne(entryId);
        if (entry == null || entry.getProfile() != profile) return "redirect:/403";

        Feature feature = featureService.findOne(featureId);
        if (feature == null || feature.getProfile() != profile) return "redirect:/403";

        FeatureAndValue featureAndValue = entryService.findFeatureAndValueById(entry.getId(), feature.getId());
        model.addAttribute("featureAndValue", featureAndValue);
        return "/user/entryFeatureEdit";

    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/features/{featureId}/delete", method = RequestMethod.GET)
    public String deleteFeature(@PathVariable Integer profileId, @PathVariable Integer entryId, @PathVariable Integer featureId){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";

        Entry entry = entryService.findOne(entryId);
        if (entry == null || entry.getProfile() != profile) return "redirect:/403";

        Feature feature = featureService.findOne(featureId);
        if (feature == null || feature.getProfile() != profile) return "redirect:/403";

        if(entryService.findEntryFeatureById(entryId, featureId) != null)
            entryService.deleteEntryFeatureById(entryId, featureId);
        return "redirect:/user/profiles/" + profileId + "/entries";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/features/new", method = RequestMethod.GET)
    public String newEntryFeature(@PathVariable Integer profileId, @PathVariable Integer entryId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";

        Entry entry = entryService.findOne(entryId);
        if (entry == null || entry.getProfile() != profile) return "redirect:/403";

        FeatureAndValue featureAndValue = new FeatureAndValue();
        featureAndValue.setEntryId(entryId);
        model.addAttribute("featureAndValue", featureAndValue);
        return "/user/entryFeatureEdit";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/features/saveOrUpdate", method = RequestMethod.POST)
    public String saveEntryFeature(@PathVariable Integer profileId, @PathVariable Integer entryId, FeatureAndValue featureAndValue) {
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";

        Entry entry = entryService.findOne(entryId);
        if (entry == null || entry.getProfile() != profile) return "redirect:/403";

        String featureName = featureAndValue.getFeatureName();
        Feature feature = featureService.findByFeatureNameAndProfile(featureName, profile);
        if(feature == null){
            feature = new Feature();
            feature.setFeatureName(featureName);
            feature.setProfile(profile);
            featureService.save(feature);
        }

        Integer featureId = feature.getId();
        String featureValue = featureAndValue.getFeatureValue();
        if(entryService.findEntryFeatureById(entryId, featureId) == null)
            entryService.saveEntryFeature(entryId, featureId, featureValue);
        else
            entryService.updateEntryFeatureByFeatureValue(entryId, featureId, featureValue);
        return "redirect:/user/profiles/" + profileId + "/entries";
    }
}
