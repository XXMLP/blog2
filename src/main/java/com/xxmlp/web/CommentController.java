package com.xxmlp.web;

import com.xxmlp.po.Comment;
import com.xxmlp.po.User;
import com.xxmlp.service.BlogService;
import com.xxmlp.service.CommentService;
import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @GetMapping("/messages/{userId}")
    public String message(@PathVariable Long userId,Model model) {
        model.addAttribute("comments", commentService.listCommentByUserId(userId));
        return "about :: commentList";
    }

    @GetMapping("/usersmessages/{userId}")
    public String usersMessage(@PathVariable Long userId,Model model) {
        model.addAttribute("comments", commentService.listCommentByUserId(userId));
        model.addAttribute("user",userService.getUser(userId));
        return "messages :: commentList";
    }


    @PostMapping("/messages")
    public String postMessage(Comment comment, HttpSession session) {
        Long userId = comment.getUser().getId();
        comment.setUser(userService.getUser(userId));
        User user = (User) session.getAttribute("user");
        if (user==null) {
            comment.setAvatar(avatar);
        }else if (user.getId()!=userId){
            comment.setAvatar(avatar);
        }else if (user.getId() == userId) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }
        commentService.saveComment(comment);
        return "redirect:/messages/" + userId;
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user==null){
            comment.setAvatar(avatar);
        } else if (user.getId() != blogService.getBlog(blogId).getUser().getId()){
            comment.setAvatar(avatar);
        }else if (user.getId() == blogService.getBlog(blogId).getUser().getId()) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }



}
