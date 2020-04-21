package com.xxmlp.web;

import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {
    @Autowired
    private UserService userService;

    @GetMapping("/about")
    public String about(Model model) {
        Long id= Long.valueOf(1);
        model.addAttribute("user",userService.getUser(id));
        return "about";
    }
}
