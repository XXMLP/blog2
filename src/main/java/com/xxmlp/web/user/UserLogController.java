package com.xxmlp.web.user;

import com.xxmlp.po.User;
import com.xxmlp.service.AdressService;
import com.xxmlp.vo.AddrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserLogController {

    @Autowired
    private AdressService adressService;

    @GetMapping("/addr")
    public String addr(@PageableDefault(size = 10,sort = {"loginTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model, AddrQuery addrQuery, HttpSession session, User user) {
        user=(User) session.getAttribute("user");
        addrQuery.setUserId(user.getId());
        model.addAttribute("page",adressService.listAddress(pageable, addrQuery));
        return "user/addr";
    }

}
