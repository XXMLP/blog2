package com.lrm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LicenseShowController {

    @GetMapping("/license")
    public String about() {
        return "license";
    }
}
