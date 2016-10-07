package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ustb.superui.ustbedu.R;

public class Whatsnew_door extends AppCompatActivity {
    private ImageView mLeft;
    private ImageView mRight;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsnew_door);
        initial();
        TranslateAnimation tran = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
        tran.setStartOffset(800);
        tran.setDuration(2000);
        tran.setFillAfter(true);
        TranslateAnimation tran2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
        tran2.setStartOffset(800);
        tran2.setDuration(2000);
        tran2.setFillAfter(true);
        AnimationSet anim = new AnimationSet(true);
        ScaleAnimation myscaleanim = new ScaleAnimation(1f,3f,1f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        myscaleanim.setStartOffset(300);
        myscaleanim.setDuration(1000);
        AlphaAnimation myalphaanim = new AlphaAnimation(1,0.0001f);
        myalphaanim.setDuration(1500);
        anim.addAnimation(myscaleanim);
        anim.addAnimation(myalphaanim);
        anim.setFillAfter(true);
        mLeft.startAnimation(tran);
        mRight.startAnimation(tran2);
        title.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClassName(Whatsnew_door.this,"com.ustb.superui.ustbedu.activity.Index");
                startActivity(intent);
                Whatsnew_door.this.finish();
            }
        },2800);
    }

    private void initial() {
        mLeft = (ImageView) findViewById(R.id.imageLeft);
        mRight = (ImageView) findViewById(R.id.imageRight);
        title = (TextView) findViewById(R.id.anim_text);
    }
}
