package com.sogou.fastomiai;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sogou.fastomiai.model.NoticeListInfo.NoticeInfo;

public class NotificationListActivity extends Activity {
    private ListView mList;
    private ImageButton mBtnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        
        mList = (ListView)findViewById(R.id.notification_list);
        
        // TODO 此处 Adapter 的数据应该是从网上拿
        ArrayList<NoticeInfo> infos = new ArrayList<NoticeInfo>();
        NoticeInfo info1 = new NoticeInfo();
        NoticeInfo info2 = new NoticeInfo();
        infos.add(info1);
        infos.add(info2);
        NotificationListAdapter listAdapter = new NotificationListAdapter(infos);
        
        mList.setAdapter(listAdapter);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_notification_list_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
    
    private class NotificationListAdapter extends ArrayAdapter<NoticeInfo> {
        public NotificationListAdapter(ArrayList<NoticeInfo> infos) {
            super(NotificationListActivity.this, 0, infos);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = NotificationListActivity.this.getLayoutInflater()
                        .inflate(R.layout.list_item_notice, null);
            }
            
            final NoticeInfo info = getItem(position);
            
            TextView textTip = (TextView)convertView.findViewById(R.id.text_notice_tip);
            if (info.title != null) {
                textTip.setText(info.title);
            }
            
            LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.layout_notice_item);
            layout.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "打开一条通知", Toast.LENGTH_SHORT).show();   
                }
            });
            
            return convertView;
        }
    }
}
