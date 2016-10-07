package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ustb.superui.ustbedu.R;

import javax.xml.datatype.Duration;

public class SelfMessage_top_btn extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_message_top_btn);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void shake(View v) {
        Intent intent = new Intent();
        intent.setClassName(this,"com.ustb.superui.ustbedu.activity.Shake");
        startActivity(intent);
    }

    public void roll(View v) {
        Intent intent = new Intent();
        intent.setClassName(this,"com.ustb.superui.ustbedu.activity.Roll_answer");
        startActivity(intent);
    }
}

