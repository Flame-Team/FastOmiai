package com.sogou.fastomiai.controller;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.BrowseActivity;
import com.sogou.fastomiai.SplashActivity;
import com.sogou.fastomiai.model.UserAuthInfo;
import com.sogou.fastomiai.model.UserGetInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;
import com.sogou.fastomiai.util.PreferenceUtil;

public class SessionManager {
    private volatile static SessionManager sInstance;
    private Context mContext;
    
    private boolean isLogin = false;
    
    private UserGetInfo mUserInfo;
    
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
        
        SharedPreferences.Editor editor = mContext.getSharedPreferences(com.android.pushclient.Constants.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE).edit();
        editor.putString(com.android.pushclient.Constants.XMPP_USERNAME, phone);
        editor.putString(com.android.pushclient.Constants.XMPP_PASSWORD, phone);
        editor.commit();
    }
    
    public boolean isLogin() {
        return isLogin;
    }
    
    public void setLoginStatus(boolean isLoginStatus) {
        isLogin = isLoginStatus;
    }
    
    public void pullCurrentUserInfo() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.TOKEN, getToken());
        String url = NetworkUtil.getUrl(Constants.USER_GET_URL, params);
        NetworkRequest.get(url, UserGetInfo.class,
                new Response.Listener<UserGetInfo>() {
                    @Override
                    public void onResponse(UserGetInfo info) {
                        if (info != null) {
                            if (info.isSuccess()) {
                                mUserInfo = info;
                            } else {
                            }
                        } else {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  
                    }
                },
                false);
    }
    
    public UserGetInfo getCurrentUserInfo() {
        return mUserInfo;
    }
}
