package com.xxmlp.web.admin;

import com.xxmlp.po.User;
import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String user(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",userService.listUser(pageable));
        return "admin/user";
    }


    @GetMapping("/user/input")
    public String input(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-input";
    }

    @GetMapping("/user/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/user-input";
    }

    @PostMapping("/user")
    public String post(@Valid User user, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        User user1 = userService.getUserByName(user.getUsername());
        if (user1 != null) {
            result.rejectValue("username","nameError","用户名已存在");
        }
        if (result.hasErrors()) {
            return "admin/user-input";
        }
        String pwd=request.getParameter("password");
        String pwd1=request.getParameter("password1");
        if (pwd.equals(pwd1)) {
        User t = userService.saveUser(user);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        }else {
            attributes.addFlashAttribute("message","两次密码输入不相同");
            return "admin/user-input";
        }
        return "redirect:/admin/user";
    }

    @PostMapping("/user/{id}")
    public String editPost(@Valid User user, BindingResult result, @PathVariable Long id, RedirectAttributes attributes, HttpServletRequest request) {
        User user1 = userService.getUserByName(user.getUsername());
        User user2 = userService.getUserByNameOrEmail(user.getEmail());
        if (user1 != null && user1.getId()!=user.getId()) {
            result.rejectValue("username","nameError","用户名已存在");
        }
        if (user2 !=null && user2.getId()!=user.getId()){
            result.rejectValue("username","nameError","邮箱已被绑定");
        }
        if (result.hasErrors()) {
            return "admin/user-input";
        }
        String pwd=request.getParameter("password");
        String pwd1=request.getParameter("password1");
        if (pwd.equals(pwd1)) {
            User u = userService.updateUser(id, user);
            if (u == null) {
                attributes.addFlashAttribute("message", "更新失败");
            } else {
                attributes.addFlashAttribute("message", "更新成功");
            }
        }else {
            attributes.addFlashAttribute("message","两次密码输入不相同");
            return "admin/user-input";
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        Integer blogSize = userService.getUser(id).getBlogs().size();
        Integer imgSize  = userService.getUser(id).getImgs().size();
        Integer typeSize = userService.getUser(id).getTypes().size();
        Integer tagSize = userService.getUser(id).getTags().size();
        if (blogSize>=1||imgSize>=1||tagSize>=1||typeSize>=1){
            attributes.addFlashAttribute("message",
                    "此用户创建了"+blogSize+"篇博客"+tagSize+"个标签"+typeSize+"个类型"+",上传了"+imgSize+"个文件，删除此用户前请先清除相关的内容");
            return "redirect:/admin/user";
        }
        if (blogSize==0&&imgSize==0&&tagSize==0&&typeSize==0){
            userService.deleteUser(id);
            attributes.addFlashAttribute("message", "删除成功");
            return "redirect:/admin/user";
        }
        return "redirect:/admin/user";
    }
}
