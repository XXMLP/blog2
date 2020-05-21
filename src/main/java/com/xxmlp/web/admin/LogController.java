package com.xxmlp.web.admin;

import com.xxmlp.service.AdressService;
import com.xxmlp.service.UserService;
import com.xxmlp.vo.AddrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class LogController {

    @Autowired
    private AdressService adressService;

    @Autowired
    private UserService userService;

    @GetMapping("/addr")
    public String addr(@PageableDefault(size = 10,sort = {"loginTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model,AddrQuery addrQuery) {
        model.addAttribute("users", userService.listUser());
        model.addAttribute("page",adressService.listAddress(pageable, addrQuery));
        return "admin/addr";
    }

    @PostMapping("/addr/search")
    public String search(@PageableDefault(size = 10, sort = {"loginTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         AddrQuery addrQuery, Model model) {
        model.addAttribute("page", adressService.listAddress(pageable, addrQuery));
        return "admin/addr :: addrList";
    }

}
