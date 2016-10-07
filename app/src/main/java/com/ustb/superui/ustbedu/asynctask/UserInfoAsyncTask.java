package com.ustb.superui.ustbedu.asynctask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ustb.superui.ustbedu.Utils.AnalysisHtml;
import com.ustb.superui.ustbedu.Utils.HttpConnection;

import java.util.ArrayList;

/**
 * Created by jstxzhangrui on 2016/9/28.
 */
public class UserInfoAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private boolean isInet = true;
    private ResponseForClass responseForClass;
    private ArrayList<String> list = new ArrayList<>();   //接受学生信息集合

    public UserInfoAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            isInet = false;
        } else if (cm.getActiveNetworkInfo() == null) {
            isInet = false;
        }
    }

    @Override
        protected Boolean doInBackground(Void... params) {
        Boolean result = false;
        if (isInet) {
            String URL = "http://seam.ustb.edu.cn:8080/jwgl/stuinfo.jsp";
            String data = HttpConnection.HttpConnectionForData(URL, context);
            if (data.length() > 0) {
                result = true;
            }
            list = AnalysisHtml.findUserInfo(data);
        }
        HttpConnection.closeConnection();
        return result;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            //通过回调函数更新信息
            responseForClass.receiveMessage(list);
        }else {
            Toast.makeText(context, "网络异常，请检查网络配置", Toast.LENGTH_SHORT).show();
        }
    }


    public interface ResponseForClass {
        void receiveMessage(ArrayList<String> list);
    }

    public void setResponseForClass(ResponseForClass responseForClass) {
        this.responseForClass = responseForClass;
    }
}
