package tk.kaicheng.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tk.kaicheng.models.Role;
import tk.kaicheng.models.User;
import tk.kaicheng.services.RoleService;
import tk.kaicheng.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by wangkaicheng on 2017/8/3.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

<<<<<<< HEAD
    // get user from security context
    private User getContextUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }
    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
=======
    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public String home(){
        return "index";
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
>>>>>>> origin/master
    public String login(){
        return "login";
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        User userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("username", "error.username",
                            "There is already a user registered");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.save(user);
            return "postRegistration";
        }

    }

    @RequestMapping(value="/postLogin", method = RequestMethod.GET)
    public String postLogin(Model model){
        User user = getContextUser();
        Set<Role> roles = user.getRoles();
        while(roles.iterator().hasNext()){
            Role role = roles.iterator().next();
            if(role.getRoleName().equals("ADMIN"))return "redirect:/admin/index";
            if(role.getRoleName().equals("USER"))return "redirect:/user/index";
        }
        return "redirect:/user/index";
    }

    @RequestMapping(value={"/admin/index"}, method = RequestMethod.GET)
    public String adminIndex(Model model){
        User user = getContextUser();
        model.addAttribute("username", "Welcome " + user.getUsername() );
        model.addAttribute("userMessage","Contents only for admin.");
        return "/admin/index";
    }

    @RequestMapping(value={"/user/index"}, method = RequestMethod.GET)
    public String userIndex(Model model){
        User user = getContextUser();
        model.addAttribute("username", "Welcome " + user.getUsername() );
        model.addAttribute("userMessage","Contents only for user.");
        return "/user/index";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
