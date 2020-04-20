package com.xxmlp.web.user;

import com.xxmlp.po.User;
import com.xxmlp.service.BlogService;
import com.xxmlp.service.TagService;
import com.xxmlp.service.TypeService;
import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class HomePageController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @GetMapping("/homepage")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, User user, HttpSession session) {
        user=(User) session.getAttribute("user");
        model.addAttribute("page",blogService.listUserBlog(pageable,user));
        model.addAttribute("totalBlogs",userService.totalBlogs(user));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listUserRecommendBlogTop(8,user));
        model.addAttribute("totalView",userService.totalView(user));
        return "user/homepage";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model, User user, HttpSession session) {
        user=(User) session.getAttribute("user");
        model.addAttribute("page", blogService.listUserSearchBlog("%"+query+"%", pageable,user));
        model.addAttribute("query", query);
        return "user/search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "user/blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model, User user, HttpSession session) {
        user=(User) session.getAttribute("user");
        model.addAttribute("newblogs", blogService.listUserNewBlogTop(3,user));
        return "_fragments :: newblogList";
    }

}
