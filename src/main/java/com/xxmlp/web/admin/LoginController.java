package com.xxmlp.web.admin;

import com.xxmlp.po.Session;
import com.xxmlp.po.User;
import com.xxmlp.service.SessionService;
import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Session userSession,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null && user.getType() == 1) {
            String sessionId = session.getId();
            session.setAttribute("user",user);
            /**设置唯一sessionId,限制同一用户多地登录*/
            if(sessionService.getSession(user.getId())==null){
                userSession.setUserId(user.getId());
                userSession.setSessionId(sessionId);
                sessionService.saveSession(userSession);
            }else{
                sessionService.delete(user.getId());
                userSession.setUserId(user.getId());
                userSession.setSessionId(sessionId);
                sessionService.saveSession(userSession);
            }
            user.setPassword(null);
            return "admin/index";
        }else if (user != null && user.getType() == 0){
            attributes.addFlashAttribute("message","你不是管理员，已切换至用户登录页面");
            return "redirect:/user";
        }else{
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        sessionService.delete(user.getId());
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
