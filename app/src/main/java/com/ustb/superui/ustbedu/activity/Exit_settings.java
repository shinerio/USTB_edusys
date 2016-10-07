package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.print.PrintJob;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.ustb.superui.ustbedu.R;

public class Exit_settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_settings);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    public void determine_cancel(View v){
        this.finish();
    }

    public void determine_exit(View v){
        this.finish();
        Index.instance.finish();
        SharedPreferences account = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor  e = account.edit();
        e.putBoolean("isSave", false);   //设置不记住密码，防止自动登录
        e.apply();
        Intent intent = new Intent();
        intent.setClassName(Exit_settings.this,"com.ustb.superui.ustbedu.activity.Login");
        startActivity(intent);
    }
}
