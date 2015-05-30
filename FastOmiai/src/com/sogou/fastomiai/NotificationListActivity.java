package com.sogou.fastomiai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.NoticeListInfo;
import com.sogou.fastomiai.model.NoticeListInfo.MsgTypeEnum;
import com.sogou.fastomiai.model.NoticeListInfo.NoticeInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;

public class NotificationListActivity extends Activity {
    private ListView mList;
    private ImageButton mBtnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        
        mList = (ListView)findViewById(R.id.notification_list);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_notification_list_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });
        
        requestNoticeInfo();
    }
    
    public void requestNoticeInfo() {
        String token = SessionManager.getInstance(getApplicationContext())
                .getToken();
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.TOKEN, token);
        String url = NetworkUtil.getUrl(Constants.NOTICE_LIST_URL, params);
        NetworkRequest.get(url, NoticeListInfo.class,
                new Response.Listener<NoticeListInfo>() {
                    @Override
                    public void onResponse(NoticeListInfo info) {
                        if (info != null && info.isSuccess()) {
                            NotificationListAdapter listAdapter = new NotificationListAdapter(
                                    info.data);
                            mList.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(NotificationListActivity.this,
                                    "获取通知列表失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationListActivity.this,
                                "获取通知列表失败", Toast.LENGTH_SHORT).show();
                    }
                },
                false);
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
                	Intent intent = new Intent(getApplicationContext(), NotificationInfoActivity.class);
                	if (info.type == MsgTypeEnum.USER_MSG) {
                	    intent.putExtra(NotificationInfoActivity.EXTRA_INVITE_ID, info.inviteId);
                	}
                	intent.putExtra(NotificationInfoActivity.EXTRA_NOTICE_ID, info.noticeID);
                    startActivity(intent);
                }
            });
            
            return convertView;
        }
    }
}
