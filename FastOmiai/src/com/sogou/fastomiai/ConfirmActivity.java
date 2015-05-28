package com.sogou.fastomiai;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ConfirmActivity extends Activity {
    
	static private long sTotalTime = 60 * 60 * 24;
	private TextView mTextCountDown = null;
    static private Timer mTimer = null; 
	
    private Button mBtnHomePage = null;
    private Button mBtnFilter = null;    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (null != savedInstanceState) {
        	sTotalTime = savedInstanceState.getLong("time", sTotalTime);
        }       
        
        setContentView(R.layout.activity_confirm);
        
        mBtnHomePage = (Button) findViewById(R.id.btn_homepage);
        mBtnHomePage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
				intent.putExtra("from", "ConfirmActivity");
			    startActivity(intent);
			}
		});
        
        mBtnFilter = (Button) findViewById(R.id.btn_filter);
        mBtnFilter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "筛选", Toast.LENGTH_SHORT).show();
			}
		});
        
        mTextCountDown = (TextView) findViewById(R.id.text_countdown);
        if (null == mTimer) {
        	mTimer = new Timer();
            TimerTask task = new TimerTask() {    
                @Override    
                public void run() {    
           
                    runOnUiThread(new Runnable() {  
                        @Override    
                        public void run() {    
                        	sTotalTime--;
                            mTextCountDown.setText(getString(R.string.confirm_wait) + "\n" +
                            		sTotalTime / (60 * 60) + ":" +
                            		(sTotalTime % (60 * 60)) / 60 + ":" + 
                            		((sTotalTime % (60 * 24)) % 60));    
                            if(sTotalTime < 0){    
                            	mTimer.cancel();      
                            }    
                        }    
                    });    
                }    
            }; 
            mTimer.schedule(task, 0, 1000);
        }
        
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("time", sTotalTime);
	} 
}

