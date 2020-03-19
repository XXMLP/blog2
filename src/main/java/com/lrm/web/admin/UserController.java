package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.UserService;
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

    @PostMapping("/user/{id}")
    public String editPost(@Valid User user, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
//        User user1 = userService.getUserByName(user.getUsername());
//        if (user1 != null) {
//            result.rejectValue("name","nameError","不能重复的分类");
//        }
        if (result.hasErrors()) {
            return "admin/user-input";
        }
        User u = userService.updateUser(id,user);
        if (u == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/user";
    }
}