package com.sogou.fastomiai;

import android.app.Application;

import com.sogou.fastomiai.util.NetworkRequest;

public class MyApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        NetworkRequest.getInstance(getApplicationContext());
    }
}
