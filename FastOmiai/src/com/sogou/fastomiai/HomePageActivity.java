package com.sogou.fastomiai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class HomePageActivity extends Activity {

    private ImageButton mBtnBack = null;
    private RelativeLayout mLayoutPhoto = null;
    private RelativeLayout mLayoutNotification = null;
    private RelativeLayout mLayoutMessage = null;
    private RelativeLayout mLayoutData = null;
    private RelativeLayout mLayoutStrategy = null;
    private RelativeLayout mLayoutHelp = null;
    private RelativeLayout mLayoutCoupon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
     
        mBtnBack = (ImageButton) findViewById(R.id.btn_homepage_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = null;
				String strFrom = getIntent().getExtras().getString("from");
		        if (strFrom.equals("ConfirmActivity")) {
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
				Toast.makeText(getApplicationContext(), "相册", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getApplicationContext(), "资料", Toast.LENGTH_SHORT).show();				
			}
		});
        
        mLayoutStrategy = (RelativeLayout) findViewById(R.id.layout_strategy);
        mLayoutStrategy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "攻略", Toast.LENGTH_SHORT).show();				
			}
		});
        
        mLayoutHelp = (RelativeLayout) findViewById(R.id.layout_help);
        mLayoutHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "帮助", Toast.LENGTH_SHORT).show();				
			}
		});
        
        mLayoutCoupon = (RelativeLayout) findViewById(R.id.layout_coupon);
        mLayoutCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "免费券", Toast.LENGTH_SHORT).show();				
			}
		});

    }
    
}
