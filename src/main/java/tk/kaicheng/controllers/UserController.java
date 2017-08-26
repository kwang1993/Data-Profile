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

import javax.servlet.http.HttpSession;
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

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public String home(){
        if(isLoggedIn()){
            System.out.println("hello guest");
        } else {
            System.out.println(getLoggedUser());
        }
        return "index";
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String login(){
        if(isLoggedIn()) {
            return "login";
        } else {
            return "redirect:/user/index";
        }
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        System.out.println("Register Get");
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        System.out.println("Register Post");
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

    @RequestMapping(value="/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess(Model model,HttpSession session){
        System.out.println("loginSuccess");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Set<Role> roles = user.getRoles();
        while(roles.iterator().hasNext()){
            Role role = roles.iterator().next();
            if(role.getRoleName().equals("ADMIN"))return "redirect:/admin/index";
            if(role.getRoleName().equals("USER"))return "redirect:/user/index";
        }
        return "redirect:/user/index";
    }

    @RequestMapping(value={"/admin", "/admin/index"}, method = RequestMethod.GET)
    public String adminIndex(Model model){
        System.out.println("adminIndex");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("username", "Welcome " + user.getUsername() );
        model.addAttribute("userMessage","Contents only for admin.");
        return "/admin/index";
    }

    @RequestMapping(value={"/user", "/user/index"}, method = RequestMethod.GET)
    public String userIndex(Model model){
        System.out.println("userIndex");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("username", "Welcome " + user.getUsername() );
        model.addAttribute("userMessage","Contents only for user.");
        return "/user/index";
    }

    @GetMapping("/403")
    public String error403() {
        System.out.println("error");
        return "/error/403";
    }

    @GetMapping("/template")
    public String template() {
        System.out.println("template");
        return "template";
    }

    private boolean isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user == null;
    }
    private String getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user.getUsername();
    }
}
