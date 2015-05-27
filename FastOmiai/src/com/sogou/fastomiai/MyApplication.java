package com.sogou.fastomiai;

import android.app.Application;
import android.content.Context;

import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.util.NetworkRequest;

public class MyApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Context context = getApplicationContext();
        NetworkRequest.getInstance(context);
        SessionManager.getInstance(context);
    }
}
