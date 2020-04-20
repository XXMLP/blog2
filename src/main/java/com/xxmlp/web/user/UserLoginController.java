package com.xxmlp.web.user;

import com.xxmlp.po.User;
import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserLoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "user/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "user/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/user";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/user";
    }

    @GetMapping("/register")
    public String registPage(){
        return "user/register";
    }

    @RequestMapping("/registers")
    public String register(HttpServletRequest request,RedirectAttributes attributes){
        String username= request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String nickname = request.getParameter("nickname");
        String avatar = request.getParameter("avatar");
        String email = request.getParameter("email");
        String str = "";
        if (password.equals(password1)) {
           User u = userService.getUserByName(username);
            if (u == null) {
                User user = new User();
                user.setUsername(username);
                user.setAvatar(avatar);
                user.setEmail(email);
                user.setNickname(nickname);
                user.setPassword(password);
                userService.saveUser(user);
                return "user/login";
            } else {
                attributes.addFlashAttribute("message","用户名已存在");
                return "redirect:/user/register";
            }
        }else{
            attributes.addFlashAttribute("message","两次密码输入不一致");
            return "redirect:/user/register";
        }
        }
    }

