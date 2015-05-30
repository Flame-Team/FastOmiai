package com.sogou.fastomiai;


import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.InviteDealInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;

public class PlacePickerActivity extends Activity {

	private ImageButton mBtnBack = null;
	private Button mBtnAgreePlace = null;
	
	private TextView mTextPlace1 = null;
	private TextView mTextPlace2 = null;
	private TextView mTextPlace3 = null;
	private TextView mTextPlace4 = null;
	private TextView mTextPlace5 = null;
	
	private CheckBox mCheckPlace1 = null;
	private CheckBox mCheckPlace2 = null;
	private CheckBox mCheckPlace3 = null;
	private CheckBox mCheckPlace4 = null;
	private CheckBox mCheckPlace5 = null;
	
	private LinearLayout mLayoutPartner = null;
	private boolean mMale = false;
	private boolean[] mPlaces = {true, false, true, false, true}; // to do 测试数据，实际要从intent中获取
	private int mInviteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        if (intent != null) {
            //mPlaces = getIntent().getBooleanArrayExtra("places"); // 暂时注释
            mInviteID = getIntent().getIntExtra("inviteid", -1);
            mMale = getIntent().getBooleanExtra("ismale", false);
        }
        
        setContentView(R.layout.activity_confirm_place);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_confirm_place_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
			    startActivity(intent);
			}
		});
        
        mLayoutPartner = (LinearLayout) findViewById(R.id.layout_partner);
        mLayoutPartner.setVisibility(mMale ? View.INVISIBLE : View.VISIBLE);
        
        mBtnAgreePlace = (Button) findViewById(R.id.btn_agree_place);
        mBtnAgreePlace.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (0 >= getCheckedCount()) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.confirm_none_hint),
							Toast.LENGTH_SHORT).show();
				}
				else {
				    inviteDeal(true);
				    
					Intent intent = new Intent(getApplicationContext(), DepositReceiverActivity.class);
				    startActivity(intent);
				}				
			}
		});
        
        mTextPlace1 = (TextView) findViewById(R.id.text_place1);
        mTextPlace2 = (TextView) findViewById(R.id.text_place2);
        mTextPlace3 = (TextView) findViewById(R.id.text_place3);
        mTextPlace4 = (TextView) findViewById(R.id.text_place4);
        mTextPlace5 = (TextView) findViewById(R.id.text_place5);
        
        mCheckPlace1 = (CheckBox) findViewById(R.id.check_place1);
        mCheckPlace1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCheckPlace1.setChecked(true);
			    	mCheckPlace2.setChecked(false);
			    	mCheckPlace3.setChecked(false);
			    	mCheckPlace4.setChecked(false);
			    	mCheckPlace5.setChecked(false);
				}
				else if (0 == getCheckedCount()) {
					mCheckPlace1.setChecked(true);
				}
			}
		});
        mCheckPlace1.setVisibility(mPlaces[0] ? View.VISIBLE : View.INVISIBLE);
        mTextPlace1.setVisibility(mPlaces[0] ? View.VISIBLE : View.INVISIBLE);
        mCheckPlace2 = (CheckBox) findViewById(R.id.check_place2);
        mCheckPlace2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCheckPlace1.setChecked(false);
			    	mCheckPlace2.setChecked(true);
			    	mCheckPlace3.setChecked(false);
			    	mCheckPlace4.setChecked(false);
			    	mCheckPlace5.setChecked(false);
				}
				else if (0 == getCheckedCount()) {
					mCheckPlace2.setChecked(true);
				}
			}
		});
        mCheckPlace2.setVisibility(mPlaces[1] ? View.VISIBLE : View.INVISIBLE);
        mTextPlace2.setVisibility(mPlaces[1] ? View.VISIBLE : View.INVISIBLE);
        mCheckPlace3 = (CheckBox) findViewById(R.id.check_place3);
        mCheckPlace3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCheckPlace1.setChecked(false);
			    	mCheckPlace2.setChecked(false);
			    	mCheckPlace3.setChecked(true);
			    	mCheckPlace4.setChecked(false);
			    	mCheckPlace5.setChecked(false);
				}
				else if (0 == getCheckedCount()) {
					mCheckPlace3.setChecked(true);
				}
			}
		});
        mCheckPlace3.setVisibility(mPlaces[2] ? View.VISIBLE : View.INVISIBLE);
        mTextPlace3.setVisibility(mPlaces[2] ? View.VISIBLE : View.INVISIBLE);
        mCheckPlace4 = (CheckBox) findViewById(R.id.check_place4);
        mCheckPlace4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCheckPlace1.setChecked(false);
			    	mCheckPlace2.setChecked(false);
			    	mCheckPlace3.setChecked(false);
			    	mCheckPlace4.setChecked(true);
			    	mCheckPlace5.setChecked(false);
				}
				else if (0 == getCheckedCount()) {
					mCheckPlace4.setChecked(true);
				}
			}
		});
        mCheckPlace4.setVisibility(mPlaces[3] ? View.VISIBLE : View.INVISIBLE);
        mTextPlace4.setVisibility(mPlaces[3] ? View.VISIBLE : View.INVISIBLE);
        mCheckPlace5 = (CheckBox) findViewById(R.id.check_place5);
        mCheckPlace5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCheckPlace1.setChecked(false);
			    	mCheckPlace2.setChecked(false);
			    	mCheckPlace3.setChecked(false);
			    	mCheckPlace4.setChecked(false);
			    	mCheckPlace5.setChecked(true);
				}
				else if (0 == getCheckedCount()) {
					mCheckPlace5.setChecked(true);
				}
			}
		});
        mCheckPlace5.setVisibility(mPlaces[4] ? View.VISIBLE : View.INVISIBLE);
        mTextPlace5.setVisibility(mPlaces[4] ? View.VISIBLE : View.INVISIBLE);
        
    }
    
    private void inviteDeal(boolean isDeal) {
        SessionManager sm = SessionManager.getInstance(this.getApplicationContext());
        String token = sm.getToken();
        if (token.isEmpty()) {
        } else {
            String strPos = "";
            for (int i = 0; i < mPlaces.length; i++) {
                strPos += mPlaces[i] ? "1" : "0";
            }            
            
            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.TOKEN, token);
            params.put(Constants.ID, String.valueOf(mInviteID));
            params.put(Constants.ACT, String.valueOf(isDeal ? 1 : 2));
            params.put(Constants.POSITION, strPos);
            String url = NetworkUtil.getUrl(Constants.INVITE_DEAL_URL, params);
            NetworkRequest.get(url, InviteDealInfo.class,
                    new Response.Listener<InviteDealInfo>() {
                        @Override
                        public void onResponse(InviteDealInfo info) {
                            if (info != null) {
                                if (info.isSuccess()) {
                                } else {
                                }
                            } else {
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {  
                        }
                    },
                    false);
        }
    }
    
    private int getCheckedCount() {
    	int nCount = 0;
    	if (mCheckPlace1.isChecked()) {
    		nCount++;
    	}
    	if (mCheckPlace2.isChecked()) {
    		nCount++;
    	}
    	if (mCheckPlace3.isChecked()) {
    		nCount++;
    	}
    	if (mCheckPlace4.isChecked()) {
    		nCount++;
    	}
    	if (mCheckPlace5.isChecked()) {
    		nCount++;
    	}
    	return nCount;
    }

}
