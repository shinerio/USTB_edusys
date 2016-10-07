package com.ustb.superui.ustbedu.asynctask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ustb.superui.ustbedu.Utils.AnalysisHtml;
import com.ustb.superui.ustbedu.Utils.HttpConnection;

import java.util.ArrayList;

/**
 * Created by jstxzhangrui on 2016/10/5.
 */
public class Select_term_AsyncTask extends AsyncTask<Void,Void,Boolean>{
    private Context context;
    private ArrayList<String> list = new ArrayList<>();   //接受学期列表集合
    private boolean isInet = true;

    @Override
    protected void onPreExecute() {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            isInet = false;
        } else if (cm.getActiveNetworkInfo() == null) {
            isInet = false;
        }
    }

    public Select_term_AsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean result = false;
        if (isInet) {
            String data = HttpConnection.HttpConnectionForData("http://seam.ustb.edu.cn:8080/jwgl/score.jsp", context);
            if (data.length() > 0) {
                result = true;
            }
            list = AnalysisHtml.findTermList(data);
        }
        return result;
    }
    @Override
    protected void onPostExecute(Boolean b) {
        if (b) {
            //设置回调函数，更新学期列表
            responseForClass.receiveMessage(list);
        } else {
            Toast.makeText(context, "网络异常，请检查网络配置", Toast.LENGTH_SHORT).show();
        }
    }

    private ResponseForClass responseForClass;

    public interface ResponseForClass {
        void receiveMessage(ArrayList<String> list);
    }

    public void setResponseForClass(ResponseForClass responseForClass) {
        this.responseForClass = responseForClass;
    }
}
