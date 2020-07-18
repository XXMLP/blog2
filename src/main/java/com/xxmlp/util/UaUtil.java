package com.xxmlp.util;

import com.xxmlp.po.Address;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public class UaUtil {


    public static Address getDeviceType(HttpServletRequest request, Address address) {
        String agent=request.getHeader("User-Agent");
//解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
//获取浏览器对象
        Browser browser = userAgent.getBrowser();
//获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

//        System.out.println("agent:"+agent);
//        System.out.println("浏览器名:"+browser.getName());
//        System.out.println("浏览器类型:"+browser.getBrowserType());
//        System.out.println("浏览器家族:"+browser.getGroup());
//        System.out.println("浏览器生产厂商:"+browser.getManufacturer());
//        System.out.println("浏览器使用的渲染引擎:"+browser.getRenderingEngine());
//        System.out.println("浏览器版本:"+userAgent.getBrowserVersion());
//
//        System.out.println("\n操作系统名:"+operatingSystem.getName());
//        System.out.println("访问设备类型:"+operatingSystem.getDeviceType());
//        System.out.println("操作系统家族:"+operatingSystem.getGroup());
//        System.out.println("操作系统生产厂商:"+operatingSystem.getManufacturer());
        address.setDeviceType(operatingSystem.getDeviceType().toString());
        address.setManufacturer(operatingSystem.getManufacturer().toString());
        address.setSysName(operatingSystem.getName());
        address.setWebName(browser.getName());
        //address.setWebVersion(userAgent.getBrowserVersion().toString());
        address.setWebType(browser.getBrowserType().toString());
        return address;
    }

}
