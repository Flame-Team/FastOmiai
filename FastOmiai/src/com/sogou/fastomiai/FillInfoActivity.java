package com.sogou.fastomiai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class FillInfoActivity extends FragmentActivity {

    private ImageButton mBtnBack = null;
    private TextView mTextTitle = null;
    private Button mBtnNext = null;
    
    private FragmentManager fragmentManager = null;
    private FillBaseInfoFragment mTab01;
    private FillPhotoFragment mTab02;
    private FillDesireFragment mTab03;
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_info);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_login_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mBtnNext.setText(getResources().getString(R.string.fill_info_next));
				if (1 == mIndex) {
					mBtnBack.setVisibility(View.INVISIBLE);
				}
				setTabSelection(mIndex - 1);
			}
		});
        
        mTextTitle = (TextView) findViewById(R.id.text_fill_info_title);
        
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (mIndex) {
				case 1:
					mBtnNext.setText(getResources().getString(R.string.fill_info_finish));
				case 0:
					mBtnBack.setVisibility(View.VISIBLE);
					setTabSelection(mIndex + 1);
					break;
				case 2:
					Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
				    startActivity(intent);
				    break;
				default:
					break;
				}
			}
		});
        
        fragmentManager = getSupportFragmentManager();
        setTabSelection(mIndex);

    }
    
    void setTabSelection(int index) {
    	
    	if (0 > index || 2 < index) {
    		return;
    	}
    	
    	mIndex = index;
    	mTextTitle.setText(getResources().getString(R.string.fill_info_title) + "(" + (mIndex + 1) + "/3)");
    	
    	FragmentTransaction transaction = fragmentManager.beginTransaction();
    	hideFragments(transaction);
    	switch (index) {
	    	case 0:
	    		if (null == mTab01) {
	    			mTab01 = new FillBaseInfoFragment();
	        		transaction.add(R.id.fragment_container, mTab01);
	        	}
	    		else {
	    			transaction.show(mTab01);
	    		}
	    		break;
	    	case 1:
	    		if (null == mTab02) {
	    			mTab02 = new FillPhotoFragment();
	        		transaction.add(R.id.fragment_container, mTab02);
	        	}
	    		else {
	    			transaction.show(mTab02);
	    		}
	    		break;
	    	case 2:
	    		if (null == mTab03) {
	    			mTab03 = new FillDesireFragment();
	        		transaction.add(R.id.fragment_container, mTab03);
	        	}
	    		else {
	    			transaction.show(mTab03);
	    		}
	    		break;
    		default:
    			break;
    	}
    	
    	transaction.commit();
    	
    }
    
    private void hideFragments(FragmentTransaction transaction) {
    	if (null != mTab01) {
    		transaction.hide(mTab01);
    	}
    	if (null != mTab02) {
    		transaction.hide(mTab02);
    	}
    	if (null != mTab03) {
    		transaction.hide(mTab03);
    	}
    }
}
