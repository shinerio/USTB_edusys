package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.asynctask.Select_term_AsyncTask;
import com.ustb.superui.ustbedu.asynctask.Update_selfmessage;
import com.ustb.superui.ustbedu.asynctask.UserInfoAsyncTask;
import com.ustb.superui.ustbedu.asynctask.UserPhotoAsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Index extends AppCompatActivity {
    private ArrayList<View> view_list = new ArrayList<>();
    private ViewPager index_pager;
    public static Index instance = null;
    private ImageView img_selfmessage, img_classtable, img_score, img_settings;
    private int currentIndex = 0;
    private ImageView img_tab_now;
    private Handler mHandler;
    private TextView now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        instance = Index.this;
        //启动Activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initial();
    }

    private void initial() {
        img_tab_now = (ImageView) findViewById(R.id.img_tab_now);
        index_pager = (ViewPager) findViewById(R.id.indexpager);
        index_pager.addOnPageChangeListener(new MyOnPageChangeListener());
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View index_selfmessage = layoutInflater.inflate(R.layout.index_selfmessage, null);
        Update_selfmessage up = new Update_selfmessage(index_selfmessage);
        up.initialIndex_selfmessage();
        View index_classtable = layoutInflater.inflate(R.layout.index_classtable, null);
        View index_score = layoutInflater.inflate(R.layout.index_score, null);
        View index_settings = layoutInflater.inflate(R.layout.index_settings, null);
        view_list.add(index_selfmessage);
        view_list.add(index_classtable);
        view_list.add(index_score);
        view_list.add(index_settings);
        now = (TextView) index_classtable.findViewById(R.id.now_time);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE");
                now.setText(formatter.format(new Date(System.currentTimeMillis())));
            }
        };
        Runnable updateTime = new Runnable(){
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
                mHandler.postDelayed(this,100);
            }
        };
        mHandler.postDelayed(updateTime,100);
        img_selfmessage = (ImageView) findViewById(R.id.img_selfmessage);
        img_selfmessage.setOnClickListener(new MyOnClickListener(0));
        img_classtable = (ImageView) findViewById(R.id.img_classtable);
        img_classtable.setOnClickListener(new MyOnClickListener(1));
        img_score = (ImageView) findViewById(R.id.img_score);
        img_score.setOnClickListener(new MyOnClickListener(2));
        img_settings = (ImageView) findViewById(R.id.img_settings);
        img_settings.setOnClickListener(new MyOnClickListener(3));
        PagerAdapter myPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return view_list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(view_list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(view_list.get(position));
                return view_list.get(position);
            }
        };
        index_pager.setAdapter(myPagerAdapter);
    }

    //页卡切换监听事件
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;    //tab切换动画
            int displayWidth = Index.this.getResources().getDisplayMetrics().widthPixels;
            int move = displayWidth / 4; //设置最少水平动画平移大小
            switch (position) {
                case 0:
                    img_selfmessage.setImageResource(R.drawable.tab_selfmessage_pressed);
                    if (currentIndex == 1) {
                        animation = new TranslateAnimation(move, 0, 0, 0);
                        img_classtable.setImageResource(R.drawable.tab_classtable_normal);
                    } else if (currentIndex == 2) {
                        animation = new TranslateAnimation(move * 2, 0, 0, 0);//注意动画并没改变实际位置，所以这里的起始位置需要偏移
                        img_score.setImageResource(R.drawable.tab_score_normal);
                    } else if (currentIndex == 3) {
                        animation = new TranslateAnimation(move * 3, 0, 0, 0);
                        img_settings.setImageResource(R.drawable.tab_settings_normal);
                    }
                    break;
                case 1:
                    img_classtable.setImageResource(R.drawable.tab_classtable_pressed);
                    if (currentIndex == 0) {
                        animation = new TranslateAnimation(0, move, 0, 0);
                        img_selfmessage.setImageResource(R.drawable.tab_selfmessage_normal);
                    } else if (currentIndex == 2) {
                        animation = new TranslateAnimation(move * 2, move, 0, 0);
                        img_score.setImageResource(R.drawable.tab_score_normal);
                    } else if (currentIndex == 3) {
                        animation = new TranslateAnimation(move * 3, move, 0, 0);
                        img_settings.setImageResource(R.drawable.tab_settings_normal);
                    }
                    break;
                case 2:
                    img_score.setImageResource(R.drawable.tab_score_pressed);
                    if (currentIndex == 0) {
                        animation = new TranslateAnimation(0, move * 2, 0, 0);
                        img_selfmessage.setImageResource(R.drawable.tab_selfmessage_normal);
                    } else if (currentIndex == 1) {
                        animation = new TranslateAnimation(move, move * 2, 0, 0);
                        img_classtable.setImageResource(R.drawable.tab_classtable_normal);
                    } else if (currentIndex == 3) {
                        animation = new TranslateAnimation(move * 3, move * 2, 0, 0);
                        img_settings.setImageResource(R.drawable.tab_settings_normal);
                    }
                    break;
                case 3:
                    if (currentIndex == 0) {
                        animation = new TranslateAnimation(0, move * 3, 0, 0);
                        img_selfmessage.setImageResource(R.drawable.tab_selfmessage_normal);
                    } else if (currentIndex == 1) {
                        animation = new TranslateAnimation(move, move * 3, 0, 0);
                        img_classtable.setImageResource(R.drawable.tab_classtable_normal);
                    } else if (currentIndex == 2) {
                        animation = new TranslateAnimation(move * 2, move * 3, 0, 0);
                        img_score.setImageResource(R.drawable.tab_score_normal);
                    }
                    img_settings.setImageResource(R.drawable.tab_settings_pressed);
                    break;
            }
            currentIndex = position;//更新当前页卡
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(150);
            img_tab_now.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //重写屏蔽键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            Intent intent = new Intent();
            intent.setClassName(Index.this,"com.ustb.superui.ustbedu.activity.Exit");
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    //tab键点击监听事件
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            index_pager.setCurrentItem(index);
        }
    }

    //主页中点击事件
    public void selfmessage_top_btn(View v) {
        Intent intent = new Intent();
        intent.setClassName(Index.this, "com.ustb.superui.ustbedu.activity.SelfMessage_top_btn");
        startActivity(intent);
    }
    //课程表点击时间
    public void select_class(View v){
       Intent intent = new Intent();
        switch (v.getId()){
            case R.id.week1:
                intent.putExtra("week","周一课表");
                break;
            case R.id.week2:
                intent.putExtra("week","周二课表");
                break;
            case R.id.week3:
                intent.putExtra("week","周三课表");
                break;
            case R.id.week4:
                intent.putExtra("week","周四课表");
                break;
            case R.id.week5:
                intent.putExtra("week","周五课表");
                break;
            case R.id.week6:
                intent.putExtra("week","周六课表");
                break;
            case R.id.week7:
                intent.putExtra("week","周日课表");
                break;
        }
        intent.setClassName(this,"com.ustb.superui.ustbedu.activity.show_class");
        startActivity(intent);
    }
    //score点击事件
    public void select_scoreByterm(View v){
      Intent intent =new Intent();
        intent.setClassName(this,"com.ustb.superui.ustbedu.activity.Term_list");
        startActivity(intent);
    }
    public  void select_scoreByName(View v){
        Intent intent = new Intent();
        intent.setClassName(this,"com.ustb.superui.ustbedu.activity.Score_byName");
        startActivity(intent);
    }
    //settings中点击事件
    public void exit_settings(View v) {
        Intent intent = new Intent();
        intent.setClassName(Index.this, "com.ustb.superui.ustbedu.activity.Exit_settings");
        startActivity(intent);
    }

    public void check_update(View v) {
        Toast.makeText(this, "已是最新版本，无需更新", Toast.LENGTH_SHORT).show();
    }

    public void about_me(View v) {
        Intent intent = new Intent();
        intent.setClassName(this,"com.ustb.superui.ustbedu.activity.About_me");
        startActivity(intent);
    }
}
