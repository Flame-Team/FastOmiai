package com.sogou.fastomiai.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.pushclient.Constants;
import com.sogou.fastomiai.util.PreferenceUtil;

public class SessionManager {
    private volatile static SessionManager sInstance;
    private Context mContext;
    
    private boolean isLogin = false;
    
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
        setLoginStatus(true);
    }
    
    public String getPhone() {
        return PreferenceUtil.getLoggedPhone(mContext);
    }
    
    public void setPhone(String phone) {
        PreferenceUtil.setLoggedPhone(mContext, phone);
        
        SharedPreferences.Editor editor = mContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE).edit();
        editor.putString(Constants.XMPP_USERNAME, phone);
        editor.commit();
    }
    
    public boolean isLogin() {
        return isLogin;
    }
    
    public void setLoginStatus(boolean isLoginStatus) {
        isLogin = isLoginStatus;
    }
}
