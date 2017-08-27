package tk.kaicheng.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

<<<<<<< HEAD
    @RequestMapping(value={"/","/login"}, method = RequestMethod.GET)
    public String login(){
        if(isLoggedIn()) {
            return "login";
        } else {
            return "redirect:/user/index";
        }
=======
    // get user from security context
    private User getContextUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public String home(){
        return "index";
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
>>>>>>> 42dc2d8a50a7ac759bfbb3fc2a93d4179166fd74
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String register(Model model){
        System.out.println("Register Get");
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
<<<<<<< HEAD
        System.out.println("Register Post");
=======
>>>>>>> 42dc2d8a50a7ac759bfbb3fc2a93d4179166fd74
        User userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("username", "error.username",
                            "There is already a user registered");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.save(user);
            return "postRegister";
        }
   }

<<<<<<< HEAD
    @RequestMapping(value="/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess(Model model,HttpSession session){
        System.out.println("loginSuccess");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
=======
    }

    @RequestMapping(value="/postLogin", method = RequestMethod.GET)
    public String postLogin(Model model){
        User user = getContextUser();
>>>>>>> 42dc2d8a50a7ac759bfbb3fc2a93d4179166fd74
        Set<Role> roles = user.getRoles();
        while(roles.iterator().hasNext()){
            Role role = roles.iterator().next();
            if(role.getRoleName().equals("ADMIN"))return "redirect:/admin/index";
            if(role.getRoleName().equals("USER"))return "redirect:/user/index";
        }
        return "redirect:/user";
    }

    @RequestMapping(value={"/admin/index"}, method = RequestMethod.GET)
    public String adminIndex(Model model){
<<<<<<< HEAD
        System.out.println("adminIndex");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
=======
        User user = getContextUser();
>>>>>>> 42dc2d8a50a7ac759bfbb3fc2a93d4179166fd74
        model.addAttribute("username", "Welcome " + user.getUsername() );
        model.addAttribute("userMessage","Contents only for admin.");
        return "/admin/index";
    }

    @RequestMapping(value={"/user/index"}, method = RequestMethod.GET)
    public String userIndex(Model model){
<<<<<<< HEAD
        System.out.println("userIndex");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
=======
        User user = getContextUser();
>>>>>>> 42dc2d8a50a7ac759bfbb3fc2a93d4179166fd74
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
