package com.lrm.web.admin;

import com.lrm.po.Comment;
import com.lrm.po.Tag;
import com.lrm.po.User;
import com.lrm.service.CommentService;
import com.lrm.service.TagService;
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
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public String comment(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",commentService.listComment(pageable));
        return "admin/comment";
    }

    @GetMapping("/comment/{id}/check")
    public String check(@PathVariable Long id, RedirectAttributes attributes){


    commentService.checkComment(id);

            attributes.addFlashAttribute("message","审核通过");

    return "redirect:/admin/comment";
    }




    @GetMapping("/comment/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/comment";
    }


}
