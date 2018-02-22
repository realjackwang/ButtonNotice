package com.button.notice.Notice;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.button.notice.R;
import com.button.notice.service.CommonRequest;
import com.button.notice.service.CommonResponse;
import com.button.notice.service.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class noticeSearch extends ListActivity implements TextView.OnEditorActionListener {
    private int width, height;
    private EditText t;
    private ListView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_search);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        t = (EditText)findViewById(R.id.editText);
        t.setWidth(width - 20);
        t.setOnEditorActionListener(this);
        a=(ListView)findViewById(android.R.id.list);

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            final String x=t.getText().toString();

            CommonRequest request = new CommonRequest();
            request.setTable("table_notice_info");
            request.setList("noticeTitle");
            request.setLikeEqualTo(t.getText().toString());
            request.Query(new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    ArrayList<HashMap<String, String>> arrayList1 = response.getDataList();
                    SimpleAdapter adapter =new SimpleAdapter(noticeSearch.this, arrayList1,R.layout.listview_item,new String[]{"noticeTitle","noticeText",},new int[]{R.id.title,R.id.info});
                    setListAdapter(adapter);
                }

                @Override
                public void fail(String failCode, String failMsg) {

                }
            });


        }
        return false;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        l=a;
        HashMap<String,String> map=(HashMap<String,String>)a.getItemAtPosition(position);
        String ID =map.get("id");
        Intent intent =new Intent(noticeSearch.this, noticeDetailActivity.class);
        intent.putExtra("id",ID);
        startActivity(intent);
    }   //Listview 点击事件
}
