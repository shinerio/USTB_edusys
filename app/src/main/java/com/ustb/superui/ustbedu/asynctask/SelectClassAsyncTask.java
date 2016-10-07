package com.ustb.superui.ustbedu.asynctask;

import android.os.AsyncTask;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.ustb.superui.ustbedu.Utils.AnalysisHtml;
import com.ustb.superui.ustbedu.Utils.HttpConnection;

import java.util.ArrayList;

public class SelectClassAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private ArrayList<String> list = new ArrayList<>();   //接受课程集合
    private Context context;
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

    public SelectClassAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String URL = "http://seam.ustb.edu.cn:8080/jwgl/index.jsp";
        Boolean result = false;
        if (isInet) {
            String data = HttpConnection.HttpConnectionForData(URL, context);
            if (data.length() > 0) {
                result = true;
            }
            list = AnalysisHtml.findClassByHtml(data);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (b) {
            //设置回调函数，更新课程表
           responseForClass.receiveMessage(list);
        } else {
            Toast.makeText(context, "网络异常，请检查网络配置", Toast.LENGTH_SHORT).show();
        }
    }
    //子线程设置属性
    private ResponseForClass responseForClass;
    //在UI线程中调用set方法，传给子线程一个接口的实现，用来在子线程回调，实现线程之间的数据通信
    public void setResponseForClass(ResponseForClass responseForClass) {
        this.responseForClass = responseForClass;
    }
    //在子线程中定义的接口，供UI线程实现，以实现在子线程中对UI线程实现回调
    public interface ResponseForClass {
        void receiveMessage(ArrayList<String> list);
    }

}
