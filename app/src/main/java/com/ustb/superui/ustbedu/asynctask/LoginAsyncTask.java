package com.ustb.superui.ustbedu.asynctask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

public class LoginAsyncTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private boolean isInet = true;  //是否联网
    private LoginAsyncTaskReponse response;

    public LoginAsyncTask(Context context) {
        this.context = context;
    }
    //判断用户是否联网

    @Override
    protected void onPreExecute() {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
            isInet = false;
        } else if (cm.getActiveNetworkInfo() == null) {
            isInet = false;
            Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
        }
    }

    //验证登录后台
    @Override
    protected String doInBackground(String... params) {
        String userName = params[0];
        String password = params[1];
        OutputStream os = null;
        HttpURLConnection con = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        String sessionID = "";
        URL realUrl;
        if (isInet) {   //有网络连接才进行验证操作
            try {
                //请求地址URL
                realUrl = new URL("http://seam.ustb.edu.cn:8080/jwgl/Login");
                //通过URL打开一个请求连接
                con = (HttpURLConnection) realUrl.openConnection();
                //设置请求方式，主要有get,post等
                con.setRequestMethod("POST");
                //设置请求头，一般需要设置一个Cooike用来验证会话sessionID,这里是登录验证，所以不用
                //con.setRequestProperty("Cookie", sessionID);
                con.setRequestProperty("Accept", "text/html");
                //是否自动重定向
                con.setInstanceFollowRedirects(false);
                //设置允许输入输出
                con.setDoInput(true);
                con.setDoOutput(true);
                String str = "username=" + userName + "&password="
                        + password + "&usertype=student";
                con.connect();
                //服务器写入请求信息
                os = con.getOutputStream();
                os.write(str.getBytes());
                os.flush();
                //获得输出流（服务器响应数据）
                is = con.getInputStream();
                baos = new ByteArrayOutputStream();
                byte data[] = new byte[1024];
                int len;
                while ((len = is.read(data)) != -1) {
                    baos.write(data, 0, len);
                }
                baos.flush();
                byte[] isLogin = baos.toByteArray();
                if (isLogin.length > 0) {
                    sessionID = "用户名密码错误";
                } else {
                    sessionID = con.getHeaderField("Set-Cookie");
                    if (sessionID != null)
                        sessionID = sessionID.substring(0, sessionID.indexOf(";"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (baos != null)
                        baos.close();
                    if (os != null)
                        os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (con != null) {
                    con.disconnect();
                }
            }
        }
        return sessionID;
    }

    //验证是否登录成功
    @Override
    protected void onPostExecute(String s) {
        if ("用户名密码错误".equals(s)) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            //调用回调函数
            callBackResult(false);
        } else if (s == null || "".equals(s)) {
            Toast.makeText(context, "连接失败，请检查网络配置", Toast.LENGTH_SHORT).show();
            //调用回调函数
            callBackResult(false);
        } else {    //登录验证成功，将此次会话缓存以供使用
            SharedPreferences sessionIDUtil = context.getSharedPreferences("sessionID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sessionIDUtil.edit();
            editor.putString("sessionID", s);
            editor.apply();
            //调用回调函数
            callBackResult(true);
        }
    }

    public void setOnResponse(LoginAsyncTaskReponse reponse) {
        this.response = reponse;
    }

    //创建接口，以供Activity实现此接口来实现回调
    public interface LoginAsyncTaskReponse {
        public void receiveMessage(boolean message);
    }

    //设置回调函数，告诉Activity登录情况
    public void callBackResult(boolean message) {
        response.receiveMessage(message);
    }

}
