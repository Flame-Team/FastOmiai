package com.sogou.fastomiai;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.FindUserInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;

public class TargetInfoActivity extends Activity {
    
    public static final String EXTRA_UID = "extra_uid";
    public static final String EXTRA_HEAD = "extra_head";
    
	private ImageButton mBtnBack = null;
	
	private String mUID;
	private String mHeadUrl;
	
	private NetworkImageView mHeadImage;
	
	private NetworkImageView mPhoto1;
	private NetworkImageView mPhoto2;
	private NetworkImageView mPhoto3;
	private NetworkImageView mPhoto4;
	
	private TextView mTag1;
	private TextView mTag2;
	private TextView mTag3;
	private TextView mTag4;
	private TextView mTag5;
	
	private TextView mTitle;
	private TextView mTargetName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_info);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_target_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
			    startActivity(intent);
			}
		});
        
        mHeadImage = (NetworkImageView)findViewById(R.id.image_my_photo);
        mPhoto1 = (NetworkImageView)findViewById(R.id.photo1);
        mPhoto2 = (NetworkImageView)findViewById(R.id.photo2);
        mPhoto3 = (NetworkImageView)findViewById(R.id.photo3);
        mPhoto4 = (NetworkImageView)findViewById(R.id.photo4);
        mTag1 = (TextView)findViewById(R.id.text_tag1);
        mTag2 = (TextView)findViewById(R.id.text_tag2);
        mTag3 = (TextView)findViewById(R.id.text_tag3);
        mTag4 = (TextView)findViewById(R.id.text_tag4);
        mTag5 = (TextView)findViewById(R.id.text_tag5);
        
        mTitle = (TextView)findViewById(R.id.text_target_title);
        mTargetName = (TextView)findViewById(R.id.text_target_name);
        
        Intent intent = getIntent();
        if (intent != null) {
            mUID = intent.getStringExtra(EXTRA_UID);
            mHeadUrl = intent.getStringExtra(EXTRA_HEAD);
            mHeadImage.setImageUrl(mHeadUrl, NetworkRequest.getInstance(getApplicationContext()).getImageLoader());
                        
            SessionManager sm = SessionManager.getInstance(this.getApplicationContext());
            String token = sm.getToken();
            if (token.isEmpty()) {
            } else {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TOKEN, token);
                params.put(Constants.UID, mUID);
                String url = NetworkUtil.getUrl(Constants.FIND_USER_URL, params);
                NetworkRequest.get(url, FindUserInfo.class,
                        new Response.Listener<FindUserInfo>() {
                            @Override
                            public void onResponse(FindUserInfo info) {
                                if (info != null && info.isSuccess()) {
                                    ArrayList<String> photos = info.data.photos;
                                    if (photos != null) {
                                        int size = photos.size();
                                        if (size >= 4) {
                                            mPhoto4.setImageUrl(
                                                    photos.get(3),
                                                    NetworkRequest
                                                            .getInstance(
                                                                    getApplicationContext())
                                                            .getImageLoader());
                                        } 
                                        
                                        if (size >= 3) {
                                            mPhoto3.setImageUrl(
                                                    photos.get(2),
                                                    NetworkRequest
                                                            .getInstance(
                                                                    getApplicationContext())
                                                            .getImageLoader());
                                        }
                                        
                                        if (size >= 2) {
                                            mPhoto2.setImageUrl(
                                                    photos.get(1),
                                                    NetworkRequest
                                                            .getInstance(
                                                                    getApplicationContext())
                                                            .getImageLoader());
                                        }
                                        
                                        if (size >= 1) {
                                            mPhoto1.setImageUrl(
                                                    photos.get(0),
                                                    NetworkRequest
                                                            .getInstance(
                                                                    getApplicationContext())
                                                            .getImageLoader());
                                        }
                                    }
                                    
                                    ArrayList<String> tags = info.data.tags;
                                    if (tags != null) {
                                        int tagCount = tags.size();
                                        if (tagCount > 5) {
                                            tagCount = 5;
                                        }
                                        switch (tagCount) {
                                        case 5:
                                            mTag5.setText(tags.get(4));
                                            mTag5.setVisibility(View.VISIBLE);
                                        case 4:
                                            mTag4.setText(tags.get(3));
                                            mTag4.setVisibility(View.VISIBLE);
                                        case 3:
                                            mTag3.setText(tags.get(2));
                                            mTag3.setVisibility(View.VISIBLE);
                                        case 2:
                                            mTag2.setText(tags.get(1));
                                            mTag2.setVisibility(View.VISIBLE);
                                        case 1:
                                            mTag1.setText(tags.get(0));
                                            mTag1.setVisibility(View.VISIBLE);
                                            break;
                                        }
                                    }
                                    
                                    String title = String.format(getResources().getString(R.string.target_title_format), info.data.name);
                                    mTitle.setText(title);
                                    String targetName = String.format(getResources().getString(R.string.target_name_format), info.data.name);
                                    mTargetName.setText(targetName);
                                } else {
                                    Toast.makeText(getApplicationContext(), "获取用户详情失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "获取用户详情失败", Toast.LENGTH_SHORT).show();
                            }
                        },
                        false);
            }
        }
    }
}
