package com.ustb.superui.ustbedu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cunoraz.gifview.library.GifView;
import com.ustb.superui.ustbedu.R;

import java.util.Random;

public class Roll_answer extends AppCompatActivity {
    private GifView roll;
    private RadioGroup roll_method;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RelativeLayout roll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_answer);
        roll_method = (RadioGroup) findViewById(R.id.roll_method);
        roll_container = (RelativeLayout) findViewById(R.id.roll_container);
        radioButton1 = (RadioButton) (roll_method.getChildAt(0));
        radioButton2 = (RadioButton) (roll_method.getChildAt(1));
        radioButton3 = (RadioButton) (roll_method.getChildAt(2));
        radioButton1.setChecked(true);
        roll = (GifView) findViewById(R.id.roll_gif);
        roll.play();
    }

    public void start_roll(View v) {
        roll_container.removeAllViews();
        Random random = new Random();
        int num = 0;
        if (radioButton1.isChecked()) {
            num = random.nextInt(3) + 1;
        } else if (radioButton2.isChecked()) {
            num = random.nextInt(4) + 1;
        } else if (radioButton3.isChecked()) {
            num = random.nextInt(6) + 1;
        }
        roll = new GifView(this);
        switch (num) {
            case 1:
                roll.setGifResource(R.drawable.roll1);
                break;
            case 2:
                roll.setGifResource(R.drawable.roll2);
                break;
            case 3:
                roll.setGifResource(R.drawable.roll3);
                break;
            case 4:
                roll.setGifResource(R.drawable.roll4);
                break;
            case 5:
                roll.setGifResource(R.drawable.roll5);
                break;
            case 6:
                roll.setGifResource(R.drawable.roll6);
                break;
        }
        roll_container.addView(roll);
        roll.play();
    }

    public void roll_back(View v){
        this.finish();
    }
}
