package com.lrm.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class InformationController {

    @GetMapping("/information")
    public String certificate() {
        return "admin/information";
    }


}
