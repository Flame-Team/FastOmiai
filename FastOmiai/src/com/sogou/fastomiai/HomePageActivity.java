package com.sogou.fastomiai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.UserGetInfo;
import com.sogou.fastomiai.util.NetworkRequest;


public class HomePageActivity extends Activity {

    private ImageButton mBtnBack = null;
    private RelativeLayout mLayoutPhoto = null;
    private RelativeLayout mLayoutNotification = null;
    private RelativeLayout mLayoutMessage = null;
    private RelativeLayout mLayoutData = null;
    private RelativeLayout mLayoutStrategy = null;
    private RelativeLayout mLayoutHelp = null;
    private RelativeLayout mLayoutCoupon = null;
    private RelativeLayout mLayoutMeeting = null;
    private RelativeLayout mLayoutWaitMeeting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        
        UserGetInfo userInfo = SessionManager.getInstance(
                getApplicationContext()).getCurrentUserInfo();
     
        mBtnBack = (ImageButton) findViewById(R.id.btn_homepage_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				String strFrom = null;
				if (intent != null) {
				    Bundle bundle = intent.getExtras();
				    if (bundle != null) {
				        strFrom = bundle.getString("from");
				    }
				}
		        if (strFrom != null && strFrom.equals("ConfirmActivity")) {
		        	intent = new Intent(getApplicationContext(), ConfirmActivity.class);
		        }
		        else if (strFrom.equals("BrowseActivity")) {
		        	intent = new Intent(getApplicationContext(), BrowseActivity.class);
		        }
			    startActivity(intent);
			}
		});
        
        mLayoutPhoto = (RelativeLayout) findViewById(R.id.layout_photo);
        mLayoutPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "相册功能尚未实现", Toast.LENGTH_SHORT).show();
			}
		});
        
        mLayoutNotification = (RelativeLayout) findViewById(R.id.layout_notification);
        mLayoutNotification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(getApplicationContext(), NotificationListActivity.class);
		        startActivity(intent);
			}
		});
        
        mLayoutMessage = (RelativeLayout) findViewById(R.id.layout_message);
        mLayoutMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                startActivity(intent);
			}
		});
        
        mLayoutData = (RelativeLayout) findViewById(R.id.layout_data);
        mLayoutData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "资料功能尚未实现", Toast.LENGTH_SHORT).show();				
			}
		});
        
        mLayoutStrategy = (RelativeLayout) findViewById(R.id.layout_strategy);
        mLayoutStrategy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "攻略功能尚未实现", Toast.LENGTH_SHORT).show();				
			}
		});
        
        mLayoutHelp = (RelativeLayout) findViewById(R.id.layout_help);
        mLayoutHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "帮助功能尚未实现", Toast.LENGTH_SHORT).show();				
			}
		});
        
        mLayoutCoupon = (RelativeLayout) findViewById(R.id.layout_coupon);
        mLayoutCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "免费券功能尚未实现", Toast.LENGTH_SHORT).show();				
			}
		});

        mLayoutMeeting = (RelativeLayout) findViewById(R.id.layout_meeting);
        mLayoutMeeting.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingActivity.class);
                startActivity(intent);
            }
        });
        
        mLayoutWaitMeeting = (RelativeLayout) findViewById(R.id.layout_wait_meeting);
        mLayoutWaitMeeting.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaitMeetingActivity.class);
                startActivity(intent);
            }
        });
        
        NetworkImageView headImage = (NetworkImageView)findViewById(R.id.image_my_photo);
        if (userInfo != null && userInfo.data != null && userInfo.data.headUrl != null) {
            headImage.setImageUrl(userInfo.data.headUrl, NetworkRequest
                    .getInstance(getApplicationContext()).getImageLoader());
        }
        
        TextView textName = (TextView)findViewById(R.id.text_homepage_name);
        textName.setText(userInfo.data.name);
        
        TextView textDatedCount = (TextView) findViewById(R.id.text_homepage_dated_count);
        String datedCount = String.format(
                getResources().getString(R.string.homepage_dating_count),
                userInfo.data.datedCount);
        textDatedCount.setText(datedCount);
        
        TextView textDatingCount = (TextView)findViewById(R.id.text_homepage_dating_count);
        textDatingCount.setText(String.valueOf(userInfo.data.choice));
        
        TextView textReputation = (TextView)findViewById(R.id.text_homepage_reputation);
        textReputation.setText(String.valueOf(userInfo.data.reputation));
    }
    
}
