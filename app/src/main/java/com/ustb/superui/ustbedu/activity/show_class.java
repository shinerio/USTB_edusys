package com.ustb.superui.ustbedu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.asynctask.SelectClassAsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class show_class extends AppCompatActivity {
    private TextView title;
    private TextView class1;
    private TextView class2;
    private TextView class3;
    private TextView class4;
    private TextView class5;
    private TextView class6;
    private TextView time_is_class;
    private TextView is_class;
    private int currentHour;
    private int currentMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class);
        intial();
        SelectClassAsyncTask selectClassAsyncTask = new SelectClassAsyncTask(this);
        selectClassAsyncTask.setResponseForClass(new SelectClassAsyncTask.ResponseForClass() {
            @Override
            public void receiveMessage(ArrayList<String> list) {
                    int step = 0;
                switch (getIntent().getStringExtra("week")){
                    case "周日课表":
                        step+=1;
                    case "周六课表":
                        step+=1;
                    case "周五课表":
                        step+=1;
                    case "周四课表":
                        step+=1;
                    case "周三课表":
                        step+=1;
                    case "周二课表":
                        step+=1;
                        break;
                }
                class1.setText(list.get(0+step));
                class2.setText(list.get(7+step));
                class3.setText(list.get(14+step));
                class4.setText(list.get(21+step));
                class5.setText(list.get(28+step));
                class6.setText(list.get(35+step));
            }
        });
        selectClassAsyncTask.execute();
        switch (whichClass()){
            case 1:
                isInClass(class1);
                break;
            case 2:
                isInClass(class2);
                break;
            case 3:
                isInClass(class2);
                break;
            case 4:
                isInClass(class4);
                break;
            case 5:
                isInClass(class5);
                break;
            case 6:
                isInClass(class6);
                break;
            case 0:
                break;
        }
    }

    private void intial() {
        title = (TextView) findViewById(R.id.class_week);
        title.setText(getIntent().getStringExtra("week"));
        class1 = (TextView) findViewById(R.id.class1);
        class2 = (TextView) findViewById(R.id.class2);
        class3 = (TextView) findViewById(R.id.class3);
        class4 = (TextView) findViewById(R.id.class4);
        class5 = (TextView) findViewById(R.id.class5);
        class6 = (TextView) findViewById(R.id.class6);
        time_is_class = (TextView) findViewById(R.id.time_is_class);
        is_class = (TextView) findViewById(R.id.is_class);
        SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分ss秒");
        time_is_class.setText(formatter.format(new Date(System.currentTimeMillis())));
        formatter = new SimpleDateFormat("HH");
        currentHour =Integer.parseInt(formatter.format(new Date(System.currentTimeMillis())));
        formatter = new SimpleDateFormat("mm");
        currentMinute=Integer.parseInt(formatter.format(new Date(System.currentTimeMillis())));
    }

    public void show_class_back(View v){
        this.finish();
    }
    //判断当前有无课程
    public void isInClass(TextView tv){
        if("无课程".equals(tv.getText().toString())){
            is_class.setText("您当前没有正在进行的课程");
        }else {
            is_class.setText("您当前有课程正在进行");
        }
    }
    //判断当前处于第几节课
    public int whichClass(){
        if(currentHour==8||(currentHour==9&&currentMinute<=35)){
            return 1;
        }else if((currentHour==9&&currentMinute>=55)||currentHour==10||(currentHour==11&&currentMinute<=30)){
            return 2;
        }else if((currentHour==13&&currentMinute>=30)||currentHour==14||(currentHour==15&&currentMinute<=05)){
            return 3;
        }else if((currentHour==15&&currentMinute>=20)||(currentHour==16&&currentMinute<=55)){
            return 4;
        }else if((currentHour==17&&currentMinute>=10)||(currentHour==18&&currentMinute<=45)){
            return 5;
        }else if((currentHour==19&&currentMinute>=30)||currentHour==20||(currentHour==21&&currentMinute<=05)){
            return 6;
        }
        return 0;
    }
}
