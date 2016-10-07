package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ustb.superui.ustbedu.R;

import java.util.ArrayList;
import java.util.List;

public class Whatsnew extends AppCompatActivity {
    private int[] images = {R.drawable.w01,R.drawable.w02,R.drawable.w03,R.drawable.w04,R.drawable.w05,R.drawable.w06};
    private List<ImageView> iv_list = new ArrayList<>();
    private ViewPager vp;
    private ImageView[] points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsnew);
        inital();
        PagerAdapter myaPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return iv_list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object ;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(iv_list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(iv_list.get(position));
                return iv_list.get(position);
            }
        };
        vp.setAdapter(myaPagerAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0;i < points.length;i++){
                    points[i].setBackgroundResource(R.drawable.point);
                }
                points[position].setBackgroundResource(R.drawable.point_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        iv_list.get(iv_list.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(Whatsnew.this,"com.ustb.superui.ustbedu.activity.Whatsnew_door");
                startActivity(intent);
                Whatsnew.this.finish();
            }
        });
    }

    private void inital() {
        LinearLayout bottom_points = (LinearLayout) findViewById(R.id.bottom_points);
        //初始化page点数组
        points = new ImageView[bottom_points.getChildCount()];
        for(int i = 0;i<bottom_points.getChildCount();i++){
            points[i] = (ImageView) bottom_points.getChildAt(i);
        }
        vp = (ViewPager) findViewById(R.id.whatsnew_viewpager);
        //将分页展示图装载入集合
        for(int i= 0; i<images.length;i++){
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(images[i]);
            iv_list.add(iv);
        }
    }
}
