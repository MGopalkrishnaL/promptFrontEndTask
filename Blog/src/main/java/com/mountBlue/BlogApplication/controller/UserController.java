package com.mountBlue.BlogApplication.controller;

import com.mountBlue.BlogApplication.Util.UserUtil;
import com.mountBlue.BlogApplication.model.User;
import com.mountBlue.BlogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserUtil userUtil;

    @GetMapping("/register")
    public String addUserView(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "userCreate";
    }

    @GetMapping("/login")
    public String checkLogin(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "login";
    }

    @PostMapping("/saveUser")
    public String addUser(@ModelAttribute("user") User userData , @RequestParam("confirmPassword") String password) {
        if (userData.getPassword().equals(password)) {
            User user = userUtil.NormalToUserObject(userData);
            user.setRole("USER");
            user.setPassword(passwordEncoder.encode(userData.getPassword()));
            userService.saveUser(user);
        } else {
            return "redirect:/register";
        }
        return "redirect:/home";
    }

}
