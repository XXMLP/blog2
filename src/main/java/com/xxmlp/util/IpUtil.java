package com.xxmlp.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {

    public static String getIpAddr() {
        String ip = "";
        // 太平洋的一个网址
        String chinaz = "http://whois.pconline.com.cn/";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            // 如有乱码的，请修改相应的编码集，这里是 gbk
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "gbk"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
            // System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 这个是之前的正则表达式,
        // Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        // 通过正则表达式匹配我们想要的内容，根据拉取的网页内容不同，正则表达式作相应的改变
        Pattern p = Pattern.compile("显示IP地址为(.*?)的位置信息");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(0);
            // 这里根据具体情况，来截取想要的内容
            ip = ipstr.substring(ipstr.indexOf("为") + 2, ipstr.indexOf("的") - 1);
            System.out.println(ip);
        }
        return ip;
    }

    public static void main(String[] args) {
        IpUtil ipUtil=new IpUtil();
        ipUtil.getIpAddr();
    }
}