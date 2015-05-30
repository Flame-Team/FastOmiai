package com.sogou.fastomiai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class WaitMeetingActivity extends Activity {
	
    private Button mBtnHomePage = null;
    private Button mBtnUber = null;
    private Button mBtnFlower = null;
    private Button mBtnCoupon = null;
    private Button mBtnMore = null;
    private Button mBtnChatList = null;
    private Button mBtnNavigation = null;
    private Button mBtnMeeting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        
        setContentView(R.layout.fragment_meeting_wait);
        
        mBtnHomePage = (Button) findViewById(R.id.btn_homepage);
        mBtnHomePage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
				intent.putExtra("from", "BrowseActivity");
			    startActivity(intent);
			}
		});
        
        mBtnUber = (Button) findViewById(R.id.btn_uber_code);
        mBtnUber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Uber码功能尚未实现", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnFlower = (Button) findViewById(R.id.btn_flower);
        mBtnFlower.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "鲜花送功能尚未实现", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnCoupon = (Button) findViewById(R.id.btn_coupon);
        mBtnCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "审美券功能尚未实现", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnMore = (Button) findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "更多", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnChatList = (Button) findViewById(R.id.btn_chat_list);
        mBtnChatList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "私信", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnNavigation = (Button) findViewById(R.id.btn_navigation);
        mBtnNavigation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "地点导航功能尚未实现", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnMeeting = (Button) findViewById(R.id.btn_meeting);
        mBtnMeeting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "正在见功能尚未实现", Toast.LENGTH_SHORT).show();
			}
		});

    }

}

