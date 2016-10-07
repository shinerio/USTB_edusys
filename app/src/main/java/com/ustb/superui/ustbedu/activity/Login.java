package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.asynctask.LoginAsyncTask;


public class Login extends AppCompatActivity {
    private Button isLogin;
    private EditText username;
    private EditText password;
    private CheckBox isSaveBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intial();
        isLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

   private void intial(){
       isLogin = (Button) findViewById(R.id.login_login_btn);
       username = (EditText) findViewById(R.id.login_user_edit);
       password = (EditText) findViewById(R.id.login_password_edit);
       isSaveBox = (CheckBox) findViewById(R.id.isSave_check);
       SharedPreferences account = getSharedPreferences("account", MODE_PRIVATE);
       Boolean isSave = account.getBoolean("isSave", false);
       if (isSave) {
           String s_userName = account.getString("userName", "");
           String p_passWord = account.getString("passWord", "");
           username.setText(s_userName);
           password.setText(p_passWord);
           isSaveBox.setChecked(true);
           doLogin();
           Login.this.finish();
       }
   }

    public void doLogin(){
        LoginAsyncTask loginTask = new LoginAsyncTask(Login.this);
        final String userName = username.getText().toString();
        final String passWord = password.getText().toString();
        loginTask.setOnResponse(new LoginAsyncTask.LoginAsyncTaskReponse() {
            @Override
            public void receiveMessage(boolean message) {
                if (message) {
                    //登录成功
                    if (isSaveBox.isChecked()) {   //保存密码到本地
                        SharedPreferences account = getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor e = account.edit();
                        e.putBoolean("isSave", true);
                        e.putString("userName", userName);
                        e.putString("passWord", passWord);
                        e.apply();
                    } else {
                        SharedPreferences account = getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor e = account.edit();
                        e.putBoolean("isSave", false);
                        e.putString("userName", "");
                        e.putString("passWord", "");
                        e.apply();
                    }
                    SharedPreferences isFirstUse = getSharedPreferences("isFirstUse", MODE_PRIVATE);
                    Boolean isViewPager = isFirstUse.getBoolean("isFirstUse",true);
                    if(isViewPager) {//第一次次使用，进行引导页
                        SharedPreferences.Editor fe = isFirstUse.edit();
                        fe.putBoolean("isFirstUse",false);
                        fe.apply();
                        Intent intent = new Intent();
                        intent.setClassName(Login.this, "com.ustb.superui.ustbedu.activity.Whatsnew");
                        startActivity(intent);
                        Login.this.finish();
                    }else{
                        //不是第一次使用,直接进主页
                        Intent intent = new Intent();
                        intent.setClassName(Login.this,"com.ustb.superui.ustbedu.activity.Index");
                        startActivity(intent);
                        Login.this.finish();
                    }
                }
            }
        });
        loginTask.execute(userName, passWord);
    }
}
