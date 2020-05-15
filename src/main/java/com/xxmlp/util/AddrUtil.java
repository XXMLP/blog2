package com.xxmlp.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 根据ip获取地址
 *
 * @author jam
 *
 */
public class AddrUtil {

        public static void main(String[] args) throws Exception {
            System.out.println(AddrUtil.getURLContent());
        }


        /**
         * 程序中访问http数据接口
         */
        public static String getURLContent() {
            String urlStr="http://whois.pconline.com.cn/ip.jsp";

            /** 网络的url地址 */
            URL url = null;
            /** http连接 */
            HttpURLConnection httpConn = null;
            /**//** 输入流 */
            BufferedReader in = null;
            StringBuffer sb = new StringBuffer();
            try {
                url = new URL(urlStr);
                in = new BufferedReader(new InputStreamReader(url.openStream(), "GBk"));
                String str = null;
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                }
            } catch (Exception ex) {

            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                }
            }
            String result = sb.toString();
            String address = result.substring(0,result.indexOf(" "));
            return address;
        }
    }