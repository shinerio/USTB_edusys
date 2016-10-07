package com.ustb.superui.ustbedu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ustb.superui.ustbedu.R;

public class Exit extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
    }

    public void exit_all(View v){
        Index.instance.finish();
        this.finish();
    }

    public void cancel_all(View v){
        this.finish();
    }
}
