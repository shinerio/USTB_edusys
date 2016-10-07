package com.ustb.superui.ustbedu.asynctask;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ustb.superui.ustbedu.R;

import java.util.ArrayList;

/**
 * Created by jstxzhangrui on 2016/10/3.
 */
public class Update_selfmessage {
    private View v;

    public Update_selfmessage(View v) {
        this.v = v;
    }

    public void initialIndex_selfmessage() {
        final TextView student_name = (TextView) v.findViewById(R.id.student_name);
        final TextView student_sex = (TextView) v.findViewById(R.id.student_sex);
        final TextView student_province = (TextView) v.findViewById(R.id.student_province);
        final TextView student_nation = (TextView) v.findViewById(R.id.student_nation);
        final TextView student_class = (TextView) v.findViewById(R.id.student_class);
        final TextView student_birthday = (TextView) v.findViewById(R.id.student_birthday);
        final TextView student_indentityNum = (TextView) v.findViewById(R.id.student_indentityNum);
        final TextView student_classgrade = (TextView) v.findViewById(R.id.student_classgrade);
        final TextView student_identity = (TextView) v.findViewById(R.id.student_identity);
        final TextView student_foreign_language = (TextView) v.findViewById(R.id.student_foreign_language);
        final TextView student_type = (TextView) v.findViewById(R.id.student_type);
        final ImageView student_photo = (ImageView) v.findViewById(R.id.student_photo);
        UserInfoAsyncTask userInfoAsyncTask = new UserInfoAsyncTask(v.getContext());
        userInfoAsyncTask.setResponseForClass(new UserInfoAsyncTask.ResponseForClass() {
            @Override
            public void receiveMessage(ArrayList<String> list) {
                student_name.setText("姓名：" + list.get(5));
                student_sex.setText("性别：" + list.get(6));
                student_province.setText("省份：" + list.get(8).replace("省", ""));
                student_nation.setText("民族：" + list.get(13));
                student_class.setText("班级：" + list.get(14));
                student_birthday.setText("出生日期：" + list.get(21));
                student_indentityNum.setText("身份证号：" + list.get(16));
                student_classgrade.setText("入学年级：" + list.get(22));
                student_identity.setText("政治面貌：" + list.get(15));
                student_foreign_language.setText("外语语种：" + list.get(23));
                student_type.setText("学生类型：" + list.get(24));
            }
        });
        userInfoAsyncTask.execute();
        UserPhotoAsyncTask userPhotoAsyncTask = new UserPhotoAsyncTask(v.getContext(),student_photo);
        userPhotoAsyncTask.execute();
    }
}
