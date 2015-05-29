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

public class DepositReceiverActivity extends Activity {

	private ImageButton mBtnBack = null;
	
	private Button mBtnReceiverCancelDate = null;
	private Button mBtnChat = null;
	
	private TextView mTextInvite = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_deposit_receiver);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_deposit_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
			    startActivity(intent);
			}
		});
        
        mTextInvite = (TextView) findViewById(R.id.text_invite);
        mTextInvite.setText("王小川" + getString(R.string.deposit_invite));
        
        mBtnReceiverCancelDate = (Button) findViewById(R.id.btn_receiver_cancel_date);
        mBtnReceiverCancelDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), NotificationListActivity.class);
			    startActivity(intent);
			}
		});
        
        mBtnChat = (Button) findViewById(R.id.btn_begin_chat);
        mBtnChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// to do
			}
		});
        
    }

}
