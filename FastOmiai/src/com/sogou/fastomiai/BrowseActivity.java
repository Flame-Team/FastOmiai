package com.sogou.fastomiai;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.pushclient.NotificationReceiver;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.FindListInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;

public class BrowseActivity extends FragmentActivity {
	
	public static final String EXTRA_USERID = "USERID";
	private static final int NUM_PAGES = 5;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	private Button mBtnHomePage = null;
	private Button mBtnFilter = null;
	private FindListInfo mFindListInfo;

	private String strConfirmID;
	private String notificationUri;
	NotificationReceiver notificationReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		
		NetworkRequest.getInstance(getApplicationContext()).startPushService();
		SessionManager.getInstance(getApplicationContext()).pullCurrentUserInfo();
		
		Intent intent = getIntent();
		if (intent != null) {
			strConfirmID = (String) intent.getStringExtra(EXTRA_USERID);
	        notificationUri = intent
	                .getStringExtra(Constants.NOTIFICATION_URI);
			getFindListData();
		}
		

		
		mBtnHomePage = (Button) findViewById(R.id.btn_homepage);
		mBtnHomePage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						HomePageActivity.class);
				intent.putExtra("from", "BrowseActivity");
				startActivity(intent);
			}
		});

		mBtnFilter = (Button) findViewById(R.id.btn_filter);
		mBtnFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "筛选功能尚未实现",
						Toast.LENGTH_SHORT).show();
			}
		});
		getFindListData();
		mPager = (ViewPager) findViewById(R.id.pager);
		

	}

	public String getID() {
		return strConfirmID;
	}

	public String getUri()
	{
		return notificationUri;
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {
			return BrowseFragment.create(BrowseActivity.this, position,
					mFindListInfo);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public void getFindListData() {
		int nStart = 0;
		int nLimit = 10;

		Map<String, String> params = new HashMap<String, String>();
		params.put("start", String.valueOf(nStart));
		params.put("limit", String.valueOf(nLimit));
		params.put("token", SessionManager.getInstance(getApplicationContext())
				.getToken());
		String url = NetworkUtil.getUrl(Constants.FIND_LIST_URL, params);

		NetworkRequest.get(url, FindListInfo.class,
				new Response.Listener<FindListInfo>() {
					@Override
					public void onResponse(FindListInfo findListInfo) {

						if (findListInfo != null) {
							mFindListInfo = findListInfo;
							// mListTags = findListInfo.data.get(0).tags;
							// mStrUrl = findListInfo.data.get(0).headUrl;
							if (findListInfo.isSuccess()) {
								mPagerAdapter = new ScreenSlidePagerAdapter(
										getSupportFragmentManager());
								mPager.setAdapter(mPagerAdapter);
							}
						} else {

						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				}, false);
	}

	protected void onNewIntent(Intent intent) {
		if (intent != null
				&& (String) intent.getStringExtra(EXTRA_USERID) != null) {
			strConfirmID = (String) intent.getStringExtra(EXTRA_USERID);
	        notificationUri = intent
	                .getStringExtra(Constants.NOTIFICATION_URI);
			getFindListData();
//			notificationReceiver = new NotificationReceiver();
//			IntentFilter filter = new IntentFilter();
//	        filter.addAction(Constants.ACTION_SHOW_NOTIFICATION);
//	        filter.addAction(Constants.ACTION_NOTIFICATION_CLICKED);
//	        filter.addAction(Constants.ACTION_NOTIFICATION_CLEARED);
//	        registerReceiver(notificationReceiver, filter);
//			
//			Intent intent1 = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
//			intent1.putExtra(Constants.NOTIFICATION_ID, "2222");
//			intent1.putExtra(Constants.NOTIFICATION_API_KEY, "222");
//			intent1.putExtra(Constants.NOTIFICATION_TITLE, "来自逗逼花花");
//			intent1.putExtra(Constants.NOTIFICATION_MESSAGE, "美女我们能合个影嘛，我想向我朋友证明其实这个世界上是有天使的");
//			intent1.putExtra(Constants.NOTIFICATION_URI, "CCCC");
//			sendBroadcast(intent1);
			
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		unregisterReceiver(notificationReceiver);
	}
}
