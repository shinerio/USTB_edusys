package com.ustb.superui.ustbedu.asynctask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ustb.superui.ustbedu.Utils.AnalysisHtml;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreAsyncTask extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private boolean isInet = true;  //是否联网
    private ScoreAsyncTaskReponse response;
    private Map<String,ArrayList> map = new HashMap<>();

    public ScoreAsyncTask(Context context) {
        this.context = context;
    }
    //判断用户是否联网

    @Override
    protected void onPreExecute() {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            isInet = false;
        } else if (cm.getActiveNetworkInfo() == null) {
            isInet = false;
        }
    }

    //验证登录后台
    @Override
    protected Boolean doInBackground(String... params) {
        Boolean result =false;
        String XNXQ = "XNXQ="+params[0];
        OutputStream os = null;
        HttpURLConnection con = null;
        InputStream is = null;
        BufferedReader br = null;
        URL realUrl;
        String data = "";
        if (isInet) {   //有网络连接才进行验证操作
            try {
                realUrl = new URL("http://seam.ustb.edu.cn:8080/jwgl/score.jsp");
                SharedPreferences sessionIDUtil = context.getSharedPreferences("sessionID", Context.MODE_PRIVATE);
                con = (HttpURLConnection) realUrl.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Cookie", sessionIDUtil.getString("sessionID",""));
                con.setRequestProperty("Accept", "text/html");
                con.setInstanceFollowRedirects(false);
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();
                os = con.getOutputStream();
                os.write(XNXQ.getBytes());
                os.flush();
                is = con.getInputStream();
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "gbk"));
                String line = "";
                while (null != (line = br.readLine())) {
                    data += line;
                }
                if(data!=null&&data.length()>0){
                    result = true;
                }
                map = AnalysisHtml.findScore(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(br!=null)
                        br.close();
                    if (is != null) {
                        is.close();
                    }
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
        return result;
    }

    //验证是否登录成功
    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            callBackResult(map);
        }else {
            Toast.makeText(context,"网络出现问题啦",Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnResponse(ScoreAsyncTaskReponse reponse) {
        this.response = reponse;
    }

    //创建接口，以供Activity实现此接口来实现回调
    public interface ScoreAsyncTaskReponse {
       void receiveMessage(Map<String,ArrayList> map );
    }

    //设置回调函数，告诉Activity登录情况
    public void callBackResult(Map<String,ArrayList> map ) {
        response.receiveMessage(map);
    }

}
