package com.xxmlp.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Github {

    public  String getJson(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection urlConnection = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public static void main(String[] args) {
       System.out.println(new Github().getJson("https://api.github.com/users/XXMLP/repos"));
    }
}