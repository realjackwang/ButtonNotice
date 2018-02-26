package com.button.notice.Discover.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.button.notice.R;
import com.button.notice.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ActivitysEnroll extends AppCompatActivity {

    ListView listView;
    List list =new ArrayList();
    ArrayAdapter<List> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys_enroll);


        listView=findViewById(R.id.listview);

        Intent intent = getIntent();
        intent.getStringExtra("ID");

        String[] lists = StringUtil.ChangetoString( intent.getStringExtra("info") );
        for(int i=0;i<lists.length;i++)
            list.add(lists[i]);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setOrientation(LinearLayout.VERTICAL);
        lineLayout.setLayoutParams(params);
        lineLayout.setGravity(Gravity.TOP );
        addView(lineLayout);
        setContentView(lineLayout);


        arrayAdapter =new ArrayAdapter<List>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

    }



    private void addView(final LinearLayout lineLayout) {
        final TextView showText = new TextView(this);

        showText.setTextColor(Color.GREEN);
        showText.setTextSize(18);
//        showText.setId(1);//设置 id
        showText.setText("我是在程序中添加的第一个文本");
        showText.setBackgroundColor(Color.BLUE);
        // set 文本大小
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //set 四周距离
        params.setMargins(10, 10, 10, 10);

        showText.setLayoutParams(params);

        //添加文本到主布局
        lineLayout.addView(showText);

    }
    }
