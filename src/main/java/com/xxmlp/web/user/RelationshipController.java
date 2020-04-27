package com.xxmlp.web.user;

import com.xxmlp.dao.UserRepository;
import com.xxmlp.po.Relationship;
import com.xxmlp.po.Type;
import com.xxmlp.po.User;
import com.xxmlp.service.RelationshipService;
import com.xxmlp.service.TypeService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class RelationshipController {
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/follows")
    public String follows(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
    HttpSession session,Model model){
    User user = (User) session.getAttribute("user");
    model.addAttribute("page",relationshipService.listFollows(user.getId(),pageable));
    return "user/follows";
    }

    @GetMapping("/fans")
    public String fans(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
    HttpSession session,Model model){
    User user = (User) session.getAttribute("user");
    model.addAttribute("page",relationshipService.listFans(user.getId(),pageable));
    return "user/fans";
    }

    @GetMapping("/friends")
    public String friends(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
    HttpSession session,Model model){
    User user = (User) session.getAttribute("user");
    model.addAttribute("page",relationshipService.listFriends(user.getId(),pageable));
    return "user/friends";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id,HttpSession session){
        User user=(User) session.getAttribute("user");
        relationshipService.removeRelationship(new Relationship(user.getId(), id));
        return "redirect:/user/follows";
    }
    @GetMapping("/attention/{id}")
    public String attention(@PathVariable Long id,HttpSession session){
        User user=(User) session.getAttribute("user");
        if (relationshipService.isAttention(user.getId(),id) == null) {
            relationshipService.saveRelationship(new Relationship(user.getId(), id));
            return "redirect:/user/fans";
        }
        return "redirect:/user/fans";
    }

}
