package tk.kaicheng.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.User;
import tk.kaicheng.services.FeatureService;
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

    // get user from security context

    private User getContextUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(auth.getName());
        return user;
    }
    @RequestMapping(value = "/user/features", method = RequestMethod.GET)
    public String list(Model model){
        User user = getContextUser();
        // get features of the user
        List<Feature> features = featureService.findByUser(user);
        model.addAttribute("features", features);
        return "/user/features";
    }

    @RequestMapping(value = "/user/feature/{id}", method = RequestMethod.GET)
    public String showFeature(@PathVariable Integer id, Model model){
        User user = getContextUser();
        // show feature belonging to the user
        Feature feature = featureService.findOne(id);
        if(feature.getUser() != user) return "redirect:/403";
        model.addAttribute("feature", feature);
        return "/user/featureshow";
    }

    @RequestMapping(value = "/user/feature/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){
        User user = getContextUser();
        // edit feature belonging to the user
        Feature feature = featureService.findOne(id);
        if(feature.getUser() != user) return "redirect:/403";
        model.addAttribute("feature", feature);
        return "/user/featureform";
    }

    @RequestMapping(value = "/user/feature/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, Model model){
        User user = getContextUser();
        // delete feature belonging to the user
        Feature feature = featureService.findOne(id);
        if(feature.getUser() != user) return "redirect:/403";
        featureService.delete(id);
        return "redirect:/user/features";
    }

    @RequestMapping(value = "/user/feature/new", method = RequestMethod.GET)
    public String newFeature(Model model){
        model.addAttribute("feature", new Feature());
        return "/user/featureform";
    }

    @RequestMapping(value = "/user/feature", method = RequestMethod.POST)
    public String saveFeature(Feature feature) {
        User user = getContextUser();
        feature.setUser(user);
        Feature fetchedFeature = featureService.findOne(feature.getId());
        if( fetchedFeature == null) {
            featureService.save(feature);
        }
        else
            featureService.updateFeatureName(feature.getId(), feature.getFeatureName());
        return "redirect:/user/feature/" + feature.getId();
    }

}
