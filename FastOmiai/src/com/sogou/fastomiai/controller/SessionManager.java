package com.sogou.fastomiai.controller;

import com.sogou.fastomiai.util.PreferenceUtil;

import android.content.Context;

public class SessionManager {
    private volatile static SessionManager sInstance;
    private Context mContext;
    
    private SessionManager(Context context) {
        mContext = context;
    }
    
    public static SessionManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SessionManager.class) {
                if (sInstance == null) {
                    sInstance = new SessionManager(context);                    
                }
            }
        }
        return sInstance;
    }
    
    public String getToken() {
        return PreferenceUtil.getLoggedToken(mContext);
    }
    
    public void setToken(String token) {
        PreferenceUtil.setLoggedToken(mContext, token);
    }
}
