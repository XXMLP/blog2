package com.xxmlp.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUitl {

    public static String getCookieValByKey(String key,HttpServletRequest request) {

        String value = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!= null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if(key.equals(cookie.getName())){
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }
}

