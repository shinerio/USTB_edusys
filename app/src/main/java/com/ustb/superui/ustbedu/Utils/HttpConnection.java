package com.ustb.superui.ustbedu.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jstxzhangrui on 2016/9/28.
 */
public class HttpConnection {
    private static HttpURLConnection con = null;
    private static InputStream is = null;
    private static BufferedReader br = null;
    public static String HttpConnectionForData(String URL, Context context) {
        SharedPreferences sessionIDUtil = context.getSharedPreferences("sessionID", Context.MODE_PRIVATE);
        String sessionID = sessionIDUtil.getString("sessionID","");
        String data = "";
        try {
            URL realUrl = new URL(URL);
            con = (HttpURLConnection) realUrl.openConnection();
            con.setRequestProperty("Accept", "text/html");
            con.setRequestProperty("Cookie", sessionID);
            con.setRequestProperty("Charset", "utf-8");
            con.setInstanceFollowRedirects(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "gbk"));
            String line = "";
            while (null != (line = br.readLine())) {
                data += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void closeConnection() {
        try {
            if (br != null) {
                br.close();
            }
            if (is != null)
                is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (con != null) {
            con.disconnect();
        }
    }
}
