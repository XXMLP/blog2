package com.lrm.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LicenseController {

    @GetMapping("/license")
    public String certificate() {
        return "admin/license";
    }

    //备案号接口
    @GetMapping("/Registered NO")
    public String RegisteredNO() {
        return "admin/Registered NO";
    }
}
