package com.ustb.superui.ustbedu.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.Utils.Lucky_message;
import com.ustb.superui.ustbedu.Utils.ShakeListener;

import java.util.Random;

public class Shake extends AppCompatActivity {
    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator;
    private RelativeLayout shakeImgUp;
    private RelativeLayout shakeImgDown;
    private Handler mHandler;
    private TextView myLuck_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
        mShakeListener = new ShakeListener(this);
        shakeImgDown = (RelativeLayout) findViewById(R.id.shakeImgDown);
        shakeImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        myLuck_message = (TextView) findViewById(R.id.luck_word);
        mHandler = new Handler();
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                Random random = new Random();
               int num =  random.nextInt(25);
                String new_luck_message = Lucky_message.getLuckMessage(num);
                if(new_luck_message!=null&&!"".equals(new_luck_message)) {
                    myLuck_message.setText(new_luck_message);
                }else {
                    myLuck_message.setText("没有摇到哟，再试一次吧！");
                }
                startAnim();  //开始 摇一摇手掌动画
                mShakeListener.stop();
                startVibrato(); //开始 震动
                mHandler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
    }

    public void startVibrato() {
        mVibrator.vibrate( new long[]{500,200,500,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1从pattern的指定下标开始重复
    }

    public void startAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(3000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        shakeImgUp.startAnimation(animup);
        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(3000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        shakeImgDown.startAnimation(animdn);
    }

    public void shake_activity_back(View v){
        this.finish();
    }

    public void for_more(View v){
        Toast.makeText(getApplicationContext(),"更多玩法，敬请期待！",Toast.LENGTH_SHORT).show();
    }
}
