package com.xxmlp.web.user;

import com.xxmlp.po.Collection;
import com.xxmlp.po.User;
import com.xxmlp.service.BlogService;
import com.xxmlp.service.CollectionService;
import com.xxmlp.service.TypeService;
import com.xxmlp.service.UserService;
import com.xxmlp.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private TypeService typeService;


    @Autowired
    private BlogService blogService;

    @GetMapping("/collection")
    public String collections(@PageableDefault(size = 8, sort = {"collected"}, direction = Sort.Direction.DESC) Pageable pageable,
    HttpSession session,Model model){
    User user = (User) session.getAttribute("user");
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page",collectionService.listCollection(user.getId(),pageable));
    return "user/collection";
    }

//    @PostMapping("/collection/search")
//    public String searchCollection(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
//                         BlogQuery blog, Model model) {
//        model.addAttribute("page", blogService.listBlog(pageable, blog));
//        return "user/collection :: blogList";
//    }


    @GetMapping("/removeCollection/{id}")
    public String remove(@PathVariable Long id,HttpSession session,RedirectAttributes attributes){
        User user=(User) session.getAttribute("user");
        collectionService.removeCollection(new Collection(user.getId(), id));
        attributes.addFlashAttribute("message", "已取消收藏");
        return "redirect:/user/collection";
    }

}
