package com.xxmlp.web;


import com.xxmlp.po.User;
import com.xxmlp.service.UserService;
import com.xxmlp.util.MD5Utils;
import com.xxmlp.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class MailController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private UserService userService;


    @GetMapping("/forget")
    public String forgetPage() {
        return "forget";
    }

    @GetMapping("/resets")
    public String reset() {
        return "resetpwd";
    }

    @PostMapping(value = "/email")
    public String forgetPass(@RequestParam String username, RedirectAttributes attributes, Model model) {
        User users = userService.getUserByNameOrEmail(username);
        if (users == null) {              //用户名不存在
            attributes.addFlashAttribute("message", "用户名不存在");
            return "redirect:/forget";
        }
        try {
            String email=userService.findEmailByUsername(username);
            String secretKey = RandomUtils.code();  //验证码
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);//30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;                  //忽略毫秒数
            users.setValidataCode(MD5Utils.code(secretKey));
            users.setRegisterDate(String.valueOf(outDate));
            userService.updateUserInfo(users);    //保存到数据库

            String emailTitle = "博客系统密码找回";
            String emailContent = "请勿回复本邮件.如果您未申请验证码，请及时修改密码，验证码："+secretKey+";     验证码将在5分钟后过期";
            sendHtmlMail(emailContent, emailTitle, email);
            attributes.addFlashAttribute("message", "操作成功,已经发送验证码到您邮箱。请在5分钟内重置密码");

        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message","邮箱不存在？未知错误,联系管理员吧。");
        }
        model.addAttribute("username",username);
        return "resetpwd";
    }

@PostMapping("/reset")
public String updatePassword(@RequestParam String username,
                             @RequestParam String key,
                             @RequestParam String password, RedirectAttributes attributes){

        User users = userService.getUserByNameKey(username,MD5Utils.code(key));

        Timestamp outDate = Timestamp.valueOf(users.getRegisterDate());
         if (outDate.getTime() <= System.currentTimeMillis()) {
             attributes.addFlashAttribute("message", "验证码已过期，请重新申请");
             return "redirect:/forget";
    }
    if (users == null) {              //用户名不存在
        attributes.addFlashAttribute("message", "验证码错误");
        return "redirect:/resets";
    }else {
        userService.resetpwd(users,password);
        users.setValidataCode(null);
        users.setRegisterDate(null);
        userService.updateUserInfo(users);
        return "user/login";
    }

}


    /**
     * 发送html格式的邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String content, String subject, String to) {
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("xxmlp980123@163.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(message);
            System.out.println("html有限已发送");
        } catch (Exception e) {
            System.out.println("发送html邮件时发生异常");
        }
    }
}
