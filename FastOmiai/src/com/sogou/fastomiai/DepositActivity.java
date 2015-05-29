package com.sogou.fastomiai;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageButton;

public class DepositActivity extends Activity {

	private ImageButton mBtnBack = null;
	
	private Button mBtnCancelDate = null;
	private Button mBtnSaveCash = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_deposit);
        
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
				Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
			    startActivity(intent);
			}
		});
        
    }

}
