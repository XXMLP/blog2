package com.xxmlp.web;

import com.xxmlp.po.Relationship;
import com.xxmlp.po.User;
import com.xxmlp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private RelationshipService relationshipService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listSearchBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
    @GetMapping("/users/{id}")
    public String homepage(@PathVariable Long id, Model model, User user,HttpSession session,
                           @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        user=userService.getUser(id);
        User fans= (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("totalBlogs", userService.totalBlogs(user));
        model.addAttribute("page",blogService.listUserBlog(pageable,user));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listUserRecommendBlogTop(8,user));
        model.addAttribute("totalView",userService.totalView(user));
        if (session.getAttribute("user")!=null &&  relationshipService.isAttention(fans.getId(),id)!=null){
            model.addAttribute("relationship","取消关注");
            model.addAttribute("guanzhu","已关注");
        }if (session.getAttribute("user")==null){
            model.addAttribute("relationship","关注");
            model.addAttribute("guanzhu","加关注");
        } if (session.getAttribute("user")!=null &&  relationshipService.isAttention(fans.getId(),id)==null){
            model.addAttribute("relationship","关注");
            model.addAttribute("guanzhu","加关注");
        } if (session.getAttribute("user")!=null &&  relationshipService.isAttention(fans.getId(),id)!=null && relationshipService.isAttention(id,fans.getId())!=null){
            model.addAttribute("relationship","互相关注");
            model.addAttribute("guanzhu","已关注");
        }
        return "homepage";
    }

    @GetMapping("/users/messages/{id}")
    public String usersMessages(@PathVariable Long id,Model model){
        model.addAttribute("user",userService.getUser(id));
        return "messages";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listNewBlogTop(3));
        return "_fragments :: newblogList";
    }

    @GetMapping("/attention/{id}")
    public String attention(@PathVariable Long id, HttpSession session, RedirectAttributes attributes){
        User user=(User) session.getAttribute("user");
        if (session.getAttribute("user")  == null){
            attributes.addFlashAttribute("message", "登录之后才能关注哦");
            return "redirect:/user";
        }else if (session.getAttribute("user") != null && relationshipService.isAttention(user.getId(),id) == null){
            relationshipService.saveRelationship(new Relationship(user.getId(), id));
            return "redirect:/users/"+ id;
        }else if (session.getAttribute("user") != null && relationshipService.isAttention(user.getId(),id) != null){
            relationshipService.removeRelationship(new Relationship(user.getId(), id));
            return "redirect:/users/"+ id;
        }
        return "redirect:/users/"+ id;
    }
}
