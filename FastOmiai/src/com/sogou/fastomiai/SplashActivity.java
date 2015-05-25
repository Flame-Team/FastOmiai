package com.sogou.fastomiai;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class SplashActivity extends Activity {
    
    private final long SPLASH_TIME_OUT = 1000L;
    
    protected static Handler handler = new Handler () {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
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
