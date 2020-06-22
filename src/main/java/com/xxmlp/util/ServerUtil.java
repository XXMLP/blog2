package com.xxmlp.util;

import ch.ethz.ssh2.Connection;

import java.io.IOException;

public class ServerUtil {

    public Boolean login(String ip,String userName,String userPwd){
        boolean flg=false;
        Connection conn;
        try {
            conn = new Connection(ip);
            conn.connect();//连接
            flg=conn.authenticateWithPassword(userName, userPwd);//认证
            if (flg){
                System.out.println("认证成功！");
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flg;
    }

    public static void main(String[] args) {
        System.out.println(new ServerUtil().login("49.232.25.149","root",""));
    }
}
