package com.sogou.fastomiai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SplashActivity extends Activity {
    
    private final long SPLASH_TIME_OUT = 1000L;
    
    private Button mBtnShowMap = null;
    
    protected static Handler handler = new Handler () {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        mBtnShowMap = (Button) findViewById(R.id.btn_show_map);
        mBtnShowMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MapActivity.class);
			    startActivity(intent);
			}
		});
        
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startHomePage();
            }
        }, SPLASH_TIME_OUT);
    }
    
    private void startHomePage() {
        // TODO 判断本地是否有登录数据和 Session，跳转到对应的页面    
    }
}
