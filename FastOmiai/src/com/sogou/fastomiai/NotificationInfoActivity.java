package com.sogou.fastomiai;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.InviteItemInfo;
import com.sogou.fastomiai.model.NoticeViewInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;


public class NotificationInfoActivity extends FragmentActivity {

    public static final String EXTRA_NOTICE_ID = "NOTICE_ID";
    public static final String EXTRA_INVITE_ID = "INVITE_ID";
    
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    
    private TextView mTitle = null;
    private ImageButton mBtnBack = null;
    
    private int mNoticeID = -1;
    private int mInviteID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_info);
        
        boolean isInvite = false;
        
        Intent intent = getIntent();
        if (intent != null) {
            mInviteID = intent.getIntExtra(EXTRA_INVITE_ID, -1);
            mNoticeID = intent.getIntExtra(EXTRA_NOTICE_ID, -1);
        }
        
        mTitle = (TextView) findViewById(R.id.text_notification_title);
        mTitle.setText(getString(R.string.notification_title) + 
				"(1/" + NUM_PAGES + ")");
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_notification_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), NotificationListActivity.class);
			    startActivity(intent);
			}
		});

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
					mTitle.setText(getString(R.string.notification_title) + 
							"(" + (mPager.getCurrentItem() + 1) + "/" + NUM_PAGES + ")");
				}
			}
		});
        
        // 获取约会详情
        getInviteDetail();
        
        // 通知已读上报
        reportNoticeReaded();
    }
    
    private void getInviteDetail() {
        SessionManager sm = SessionManager.getInstance(this);
        String token = sm.getToken();
        
        // 获取约会详情
        if (mInviteID != -1) {
            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.TOKEN, token);
            params.put(Constants.ID, String.valueOf(mInviteID));
            String url = NetworkUtil.getUrl(Constants.INVITE_ITEM_URL, params);
            NetworkRequest.get(url, InviteItemInfo.class,
                    new Response.Listener<InviteItemInfo>() {
                        @Override
                        public void onResponse(InviteItemInfo inviteItem) {
                            if (inviteItem != null && inviteItem.isSuccess()) {
                                // TODO inviteItem 里存放的有本次约会信息，应该根据它来设置界面状态
                            } else {                                
                                Toast.makeText(getApplicationContext(), "获取约会信息出错", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {  
                            Toast.makeText(getApplicationContext(), "获取约会信息出错", Toast.LENGTH_SHORT).show();
                        }
                    },
                    false);
        }
    }
    
    private void reportNoticeReaded() {
        SessionManager sm = SessionManager.getInstance(this);
        String token = sm.getToken();
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.TOKEN, token);
        params.put(Constants.ID, String.valueOf(mNoticeID));
        String url = NetworkUtil.getUrl(Constants.NOTICE_VIEW_URL, params);
        NetworkRequest.get(url, NoticeViewInfo.class,
                new Response.Listener<NoticeViewInfo>() {
                    @Override
                    public void onResponse(NoticeViewInfo authInfo) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }, false);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NotificationInfoFragment.create(getApplicationContext(), position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
