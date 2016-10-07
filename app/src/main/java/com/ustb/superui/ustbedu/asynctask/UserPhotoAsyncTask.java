package com.ustb.superui.ustbedu.asynctask;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jstxzhangrui on 2016/10/3.
 */
public class UserPhotoAsyncTask  extends AsyncTask<Void, Void, String> {
    private Context context;
    private HttpURLConnection con;
    private  InputStream is;
    private String sessionID;
    private Bitmap bitmap;
    private ImageView photo;
    public UserPhotoAsyncTask(Context context,ImageView photo){
      this.context = context;
        this.photo = photo;
    }
    @Override
    protected void onPreExecute() {
        SharedPreferences sessionIDUtil = context.getSharedPreferences("sessionID", Context.MODE_PRIVATE);
        sessionID = sessionIDUtil.getString("sessionID","");
    }

    @Override
    protected String doInBackground(Void... params) {
        String url = "http://seam.ustb.edu.cn:8080/jwgl/Stuphoto";
        try {
            URL realUrl = new URL(url);
            con = (HttpURLConnection) realUrl.openConnection();
            con.setRequestProperty("Cookie", sessionID);
            con.setRequestProperty("Accept","image/webp,image/*,*/*;q=0.8");
            con.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            is = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if(con!=null)
                    con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    @Override
    protected void onPostExecute(String str) {
        if (bitmap != null) {
            photo.setImageBitmap(bitmap);
        }
    }
}
