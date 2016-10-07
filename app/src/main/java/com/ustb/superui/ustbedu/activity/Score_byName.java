package com.ustb.superui.ustbedu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.asynctask.ScoreAsyncTask;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Score_byName extends AppCompatActivity {
    private LinearLayout container;
    private EditText search_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_by_name);
        container = (LinearLayout) findViewById(R.id.search_score_container);
        search_score = (EditText) findViewById(R.id.search_score);
    }

    public void score_byName_back(View v){
        this.finish();
    }

    public void start_search(View v){
        container.removeAllViews();
        final String classForSearch = search_score.getText().toString();
        if(classForSearch==null||"".equals(classForSearch)){
            Toast.makeText(this,"请输入待查找课程名",Toast.LENGTH_SHORT).show();
        }else{
            ScoreAsyncTask s = new ScoreAsyncTask(this);
            s.setOnResponse(new ScoreAsyncTask.ScoreAsyncTaskReponse() {
                @Override
                public void receiveMessage(Map<String, ArrayList> map) {
                    Set<String> keys = map.keySet();
                    LayoutInflater layoutInflater = LayoutInflater.from(Score_byName.this);
                    int count =0;
                    for(String key:keys){
                        if(key.contains(classForSearch)){
                            View score_item = layoutInflater.inflate(R.layout.score_item, null);
                            TextView score_xueshi = (TextView) score_item.findViewById(R.id.score_xueshi);
                            TextView score_name = (TextView) score_item.findViewById(R.id.score_name);
                            TextView score_type = (TextView) score_item.findViewById(R.id.score_type);
                            TextView score_num = (TextView) score_item.findViewById(R.id.score_num);
                            TextView score_xuefen = (TextView) score_item.findViewById(R.id.score_xuefen);
                            ArrayList<String> list = map.get(key);
                            score_name.setText(key);
                            score_type.setText(list.get(3));
                            score_num.setText("分数:  "+list.get(2));
                            score_xuefen.setText("学分:  "+list.get(0));
                            score_xueshi.setText("学时:  "+list.get(1));
                            if(count==0){
                                score_item.setBackgroundResource(R.drawable.class_info_first);
                            }else{
                                score_item.setBackgroundResource(R.drawable.class_info_item);
                            }
                            count++;
                            container.addView(score_item);
                        }
                    }
                    if(count==0&&container.getChildCount()==0){
                        View score_item = layoutInflater.inflate(R.layout.score_item, null);
                        TextView score_name = (TextView) score_item.findViewById(R.id.score_name);
                        score_name.setText("未找到匹配课程");
                        score_item.setBackgroundResource(R.drawable.class_info_item);
                        container.addView(score_item);
                    }else {
                        container.getChildAt(container.getChildCount()-1).setBackgroundResource(R.drawable.class_info_last);
                    }
                }
            });
            s.execute("all");
        }
    }
}
