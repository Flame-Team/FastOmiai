package com.sogou.fastomiai;


import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.InviteSendInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class DepositActivity extends Activity {

	private ImageButton mBtnBack = null;
	
	private Button mBtnCancelDate = null;
	private Button mBtnSaveCash = null;

	private String mStrUID;
	private String mPlaces;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_deposit);
        
        Intent intent = getIntent();
        if (intent != null) {
            mStrUID = intent.getStringExtra(MapChoosePlaceActivity.EXTRA_USERID);
            mPlaces = intent.getStringExtra(MapChoosePlaceActivity.EXTRA_PLACES);
        }
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_deposit_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
			    startActivity(intent);
			}
		});
        
        mBtnCancelDate = (Button) findViewById(R.id.btn_sponsor_cancel_date);
        mBtnCancelDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MapChoosePlaceActivity.class);
			    startActivity(intent);
			}
		});
        mBtnSaveCash = (Button) findViewById(R.id.btn_deposit_agree);
        mBtnSaveCash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    
                
                SessionManager sm = SessionManager
                        .getInstance(DepositActivity.this
                                .getApplicationContext());
                String token = sm.getToken();
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TOKEN, token);
                params.put(Constants.UID, mStrUID);
                params.put(Constants.POS, mPlaces);
                String url = NetworkUtil.getUrl(Constants.INVITE_SEND_URL, params);
                NetworkRequest.get(url, InviteSendInfo.class,
                        new Response.Listener<InviteSendInfo>() {
                            @Override
                            public void onResponse(InviteSendInfo sendInfo) {
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {  
                            }
                        },
                        false);
			    
				Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
				intent.putExtra(MapChoosePlaceActivity.EXTRA_USERID, mStrUID);
			    startActivity(intent);
			}
		});
        
    }

}
