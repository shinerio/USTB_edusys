package com.ustb.superui.ustbedu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ustb.superui.ustbedu.R;
import com.ustb.superui.ustbedu.asynctask.ScoreAsyncTask;
import com.ustb.superui.ustbedu.asynctask.Select_term_AsyncTask;

import java.util.ArrayList;

public class Term_list extends AppCompatActivity {
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        container = (LinearLayout) findViewById(R.id.term_container);
        Select_term_AsyncTask select_term_asyncTask = new Select_term_AsyncTask(this);
        select_term_asyncTask.setResponseForClass(new Select_term_AsyncTask.ResponseForClass() {
            @Override
            public void receiveMessage(ArrayList<String> list) {
                for (int i = 0 ;i<list.size();i++) {
                    LayoutInflater layoutInflater = LayoutInflater.from(Term_list.this);
                    View term_item = layoutInflater.inflate(R.layout.term_item, null);
                    if(i==0){
                        term_item.setBackgroundResource(R.drawable.preference_first_item);
                    }else if(i==list.size()-1){
                        term_item.setBackgroundResource(R.drawable.preference_last_item);
                    }else {
                        term_item.setBackgroundResource(R.drawable.preference_item);
                    }
                    TextView tv = (TextView) term_item.findViewById(R.id.term_item_name);
                    final String term_name = list.get(i);
                    tv.setText(term_name);
                    term_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("term_name",term_name);
                            intent.setClassName(Term_list.this,"com.ustb.superui.ustbedu.activity.Score_list");
                            startActivity(intent);
                        }
                    });
                    container.addView(term_item);
                }
            }
        });
        select_term_asyncTask.execute();
    }

    public void term_list_back(View v) {
        this.finish();
    }
}
