package com.ustb.superui.ustbedu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.asynctask.ScoreAsyncTask;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Score_list extends AppCompatActivity {
    private LinearLayout container;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
        container = (LinearLayout) findViewById(R.id.score_container);
        title = (TextView) findViewById(R.id.score_title);
        String term_name = getIntent().getStringExtra("term_name");
        if(term_name!=null){
            title.setText(term_name+"成绩");
        }
        if("全部学期".equals(term_name)){
            term_name ="all";
        }
        ScoreAsyncTask s = new ScoreAsyncTask(this);
        s.setOnResponse(new ScoreAsyncTask.ScoreAsyncTaskReponse() {
            @Override
            public void receiveMessage(Map<String, ArrayList> map) {
                Set<String> keys = map.keySet();
                LayoutInflater layoutInflater = LayoutInflater.from(Score_list.this);
                int count =0;
                if(keys.size()==0){
                    View score_item = layoutInflater.inflate(R.layout.score_item, null);
                    TextView score_name = (TextView) score_item.findViewById(R.id.score_name);
                    score_name.setText("当前学期暂无成绩");
                    score_item.setBackgroundResource(R.drawable.class_info_item);
                    container.addView(score_item);
                }
                for(String key:keys){
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
                    }else if(count==keys.size()-1){
                        score_item.setBackgroundResource(R.drawable.class_info_last);
                    }else{
                        score_item.setBackgroundResource(R.drawable.class_info_item);
                    }
                    count++;
                    container.addView(score_item);
                }
            }
        });
        s.execute(term_name);
    }

    public void score_list_back(View v){
        this.finish();
    }
}
