package com.button.notice.Discover.Activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.button.notice.Button.buttonLogin;
import com.button.notice.R;
import com.button.notice.User.Settings;
import com.button.notice.util.ACache;
import com.button.notice.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivitysEnter extends AppCompatActivity {
    private AlertDialog alertDialog;
    ListView listView;
    List list =new ArrayList();

    ArrayAdapter<List> arrayAdapter;


     boolean selected[] = {false, false, false, false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_enter);
        listView =findViewById(R.id.listview);


        ACache aCache = ACache.get(this);
        if(aCache.getAsString("enter")!=null){

            String[] lists =StringUtil.ChangetoString( aCache.getAsString("enter"));
            for(int i=0;i<lists.length;i++)
            list.add(lists[i]);

            selected= (boolean[]) aCache.getAsObject("selected");
            arrayAdapter =new ArrayAdapter<List>(ActivitysEnter.this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(arrayAdapter);
        }

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


               final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitysEnter.this);
               // 添加按钮的单击事件
               // 设置显示信息
               // 单击事件
               builder.setTitle("删除");
               builder.setMessage("确定要删除这一行吗？")
                       .
                       // 设置确定按钮
                               setPositiveButton("删除",
                               (dialog, which) -> {

                                   list.remove(position);
                                   arrayAdapter =new ArrayAdapter<List>(ActivitysEnter.this,android.R.layout.simple_list_item_1,list);
                                   listView.setAdapter(arrayAdapter);

                               }   ).
                       // 设置取消按钮
                               setNegativeButton("取消",
                               (dialog, which) -> {

                               });
               // 创建对话框
              AlertDialog ad = builder.create();
               // 显示对话框
               ad.show();

               return false;
           }
       });

    }

    public void infopicker(View view){

            final String[] items = {"姓名","年龄","名族","学院","专业","班级","学号","手机号","QQ","邮箱"};

            // 创建一个AlertDialog建造者
            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
            // 设置标题
            alertDialogBuilder.setTitle("可选信息列表");
            // 参数介绍
            // 第一个参数：弹出框的信息集合，一般为字符串集合
            // 第二个参数：被默认选中的，一个布尔类型的数组
            // 第三个参数：勾选事件监听
            alertDialogBuilder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    // dialog：不常使用，弹出框接口
                    // which：勾选或取消的是第几个
                    // isChecked：是否勾选
                    if (isChecked) {
                        // 选中
                        list.add(items[which]);
                    }else {
                        // 取消选中
                        list.remove(items[which]);
                    }

                }
            });
            alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //TODO 业务逻辑代码



                        arrayAdapter =new ArrayAdapter<List>(ActivitysEnter.this,android.R.layout.simple_list_item_1,list);
                        listView.setAdapter(arrayAdapter);

                    Log.e("hongliang", "" + list.toString() );
                    // 关闭提示框
                    alertDialog.dismiss();
                }
            });
            alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO 业务逻辑代码

                    // 关闭提示框
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();

    }


    public void more(View view){
        ACache aCache = ACache.get(this);
        aCache.put("enter",StringUtil.listToString(list));
        aCache.put("selected",selected);
        Log.d("enter",StringUtil.listToString(list));
        finish();
    }


    public void back(View view){

        final EditText editText = new EditText(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("请填写添加的内容");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.add(editText.getText().toString());
                arrayAdapter =new ArrayAdapter<List>(ActivitysEnter.this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(arrayAdapter);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();



    }

    public void onBackPressed() {   //防止错按返回退出


    }


}
