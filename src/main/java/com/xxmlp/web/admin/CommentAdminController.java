package com.xxmlp.web.admin;

import com.xxmlp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/newcomment")
    public String comment_new(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",commentService.listNewComment(pageable));
        return "admin/newcomment";
    }
    @GetMapping("/oldcomment")
    public String comment_old(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",commentService.listOldComment(pageable));
        return "admin/oldcomment";
    }
    @GetMapping("/allcomment")
    public String comment_all(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",commentService.listAllComment(pageable));
        return "admin/allcomment";
    }

    @GetMapping("/comment/{id}/check")
    public String check(@PathVariable Long id, RedirectAttributes attributes){


    commentService.checkComment(id);

            attributes.addFlashAttribute("message","审核通过");

    return "redirect:/admin/newcomment";
    }




    @GetMapping("/comment/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes, HttpServletRequest request) {
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message", "删除成功");
        //获取当前页面的url地址
        String url = request.getHeader("referer");
        //System.out.println(url);
        //重定向到当前页面
        return "redirect:" + url;
    }


}
