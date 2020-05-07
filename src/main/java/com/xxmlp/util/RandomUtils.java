package com.xxmlp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RandomUtils {

    public static String code(){
        String randomcode = "";
        // 用字符数组的方式随机
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 4; j++) {
            char c = m[(int) (Math.random() * 36)];
            // 保证六位随机数之间没有重复的
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        return randomcode;
    }


    public static void main(String[] args) {
        System.out.println(code());
    }
}
