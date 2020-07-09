package com.xxmlp.web.user;

import com.xxmlp.po.Address;
import com.xxmlp.po.Session;
import com.xxmlp.po.User;
import com.xxmlp.service.AdressService;
import com.xxmlp.service.SessionService;
import com.xxmlp.service.UserService;
import com.xxmlp.util.IP.IPSeeker;
import com.xxmlp.util.IPUtil;
import com.xxmlp.util.UaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@RequestMapping("/user")
public class UserLoginController {


    @Autowired
    private UserService userService;
    @Autowired
    private AdressService adressService;
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String loginPage() {
        return "user/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String code,
                        @RequestParam String code2,
                        HttpSession session,
                        Address address,
                        Session userSession,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes attributes) throws Exception {
        User user = userService.checkUser(username, password);
        if (!code.equalsIgnoreCase(code2)){
            attributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/user";
        }
        if (user != null && code.equalsIgnoreCase(code2)) {
            String sessionId = session.getId();
            session.setAttribute("user",user);
            /**将登录日志存入数据库*/
            address.setIp(IPUtil.getIpAddress(request));
            address.setAddress(new IPSeeker(new File("/root/qqwry.dat")).getCountry(IPUtil.getIpAddress(request)));//服务器路径
            //address.setNetType(new IPSeeker(new File("src/main/java/com/xxmlp/util/IP/qqwry.dat")).getIsp(IPUtil.getIpAddress(request)));//本地路径
            address.setUserId(user.getId());
            address.setUsername(user.getUsername());
            UaUtil.getDeviceType(request,address);
            address.setNetType(new IPSeeker(new File("/root/qqwry.dat")).getIsp(IPUtil.getIpAddress(request)));//服务器路径
            //address.setNetType(new IPSeeker(new File("src/main/java/com/xxmlp/util/IP/qqwry.dat")).getIsp(IPUtil.getIpAddress(request)));//本地路径
            adressService.save(address);
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
            return "user/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/user";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        sessionService.delete(user.getId());
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
        if (password.equals(password1)) {
           User u = userService.getUserByName(username);
           User u2=userService.getUserByNameOrEmail(email);
           if (u2!=null){
               attributes.addFlashAttribute("message","邮箱已被绑定");
               return "redirect:/user/register";
           }
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


