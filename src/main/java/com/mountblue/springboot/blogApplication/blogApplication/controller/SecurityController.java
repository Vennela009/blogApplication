package com.mountblue.springboot.blogApplication.blogApplication.controller;

import com.mountblue.springboot.blogApplication.blogApplication.entity.User;
import com.mountblue.springboot.blogApplication.blogApplication.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller

public class SecurityController {
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService){
        this.securityService = securityService;
    }

    @GetMapping("/showLoginPage")
    public String loginUser(){
        return "/user/login-form";
    }

    @GetMapping("/user/new")
    public String newRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "/user/register-form";
    }

    @PostMapping("/user/register")
    public String createNewUser (User user, Model model){
        Set<String> userNameSet = securityService.getUserName();
        Set<String> emailSet = securityService.getEmail();

        String url = "";
        String newUserName = user.getName();
        String newUserEmail = user.getEmail();

        if(userNameSet.contains(newUserName) || emailSet.contains(newUserEmail)){
            model.addAttribute("user", new User());
            model.addAttribute("error", "Username or email is already exist.");
            url = "/user/register-form";

        }else{
            securityService.newUserRegister(user);
            model.addAttribute("message","Success of your Register");
            url ="/user/login-form";
        }

        return url;
    }


}
