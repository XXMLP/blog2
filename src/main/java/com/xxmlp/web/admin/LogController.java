package com.xxmlp.web.admin;

import com.xxmlp.po.Type;
import com.xxmlp.po.User;
import com.xxmlp.service.AdressService;
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
@RequestMapping("/admin")
public class LogController {

    @Autowired
    private AdressService adressService;

    @GetMapping("/addr")
    public String addr(@PageableDefault(size = 10,sort = {"loginTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",adressService.listAllLog(pageable));
        return "admin/addr";
    }

}
