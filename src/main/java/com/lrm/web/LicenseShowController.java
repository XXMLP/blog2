package com.lrm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LicenseShowController {
//域名证书接口
    @GetMapping("/license")
    public String certificate() {
        return "license";
    }
    //备案号接口
    @GetMapping("/Registered NO")
    public String RegisteredNO() {
        return "Registered NO";
    }
}
