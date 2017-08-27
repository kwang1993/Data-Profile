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
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.services.FeatureService;
import tk.kaicheng.services.EntryService;
import tk.kaicheng.services.ProfileService;
import tk.kaicheng.services.UserService;

import java.util.List;


/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class EntryController {
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    private Profile getContextProfile(Integer profileId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Profile profile = profileService.findOne(profileId);
        if (profile.getUser() != user) return null;
        return profile;
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries", method = RequestMethod.GET)
    public String list(@PathVariable Integer profileId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";

        // get entries of the profile
        List<Entry> entries = entryService.findByProfile(profile);
        model.addAttribute("profile", profile);
        model.addAttribute("entries", entries);
        return "/user/entries";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/view", method = RequestMethod.GET)
    public String showEntry(@PathVariable Integer profileId, @PathVariable Integer entryId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // view entry belonging to the user
        Entry entry = entryService.findOne(entryId);
        if(entry == null || entry.getProfile() != profile) return "redirect:/403";
        model.addAttribute("profile", profile);
        model.addAttribute("entry", entry);
        List<FeatureAndValue> featureAndValues = entryService.findFeatureAndValuesByEntryId(entry.getId());
        model.addAttribute("featureAndValues", featureAndValues);
        return "/user/entryView";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer profileId, @PathVariable Integer entryId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // edit entry belonging to the user
        Entry entry = entryService.findOne(entryId);
        if(entry == null || entry.getProfile() != profile) return "redirect:/403";
        model.addAttribute("profile", profile);
        model.addAttribute("entry", entry);
        return "/user/entryEdit";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/{entryId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable Integer profileId, @PathVariable Integer entryId, Model model){
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // delete entry belonging to the user
        Entry entry = entryService.findOne(entryId);
        if(entry == null || entry.getProfile() != profile) return "redirect:/403";

        entryService.delete(entryId);
        return "redirect:/user/profiles/" + profileId + "/entries";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/new", method = RequestMethod.GET)
    public String newEntry(@PathVariable Integer profileId, Model model){

        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        // new entry
        model.addAttribute("profile", profile);
        model.addAttribute("entry", new Entry());
        return "/user/entryEdit";
    }

    @RequestMapping(value = "/user/profiles/{profileId}/entries/saveOrUpdate", method = RequestMethod.POST)
    public String saveEntry(@PathVariable Integer profileId, Entry entry) {
        // post entry
        Profile profile = getContextProfile(profileId);
        if (profile == null) return "redirect:/403";
        entry.setProfile(profile);
        Entry fetchedEntry = entryService.findByEntryNameAndProfile(entry.getEntryName(), profile);
        if( fetchedEntry == null) {
            entryService.save(entry);
        }
        else
            entryService.updateEntryName(entry.getId(), entry.getEntryName());
        return "redirect:/user/profiles/" + profileId + "/entries";
    }

}
