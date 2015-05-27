package com.sogou.fastomiai;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.UserAuthInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;


public class SplashActivity extends Activity {
    
    private final long SPLASH_TIME_OUT = 1000L;
    
    protected Handler mHandler = new Handler () {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startHomePage();
            }
        }, SPLASH_TIME_OUT);
    }
    
    private void startHomePage() {
        SessionManager sm = SessionManager.getInstance(this);
        String token = sm.getToken();
        if (token.isEmpty()) {
            startMapActivity();
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.TOKEN, token);
            String url = NetworkUtil.getUrl(Constants.USER_AUTH_URL, params);
            NetworkRequest.get(url, UserAuthInfo.class,
                    new Response.Listener<UserAuthInfo>() {
                        @Override
                        public void onResponse(UserAuthInfo authInfo) {
                            if (authInfo != null) {
                                if (authInfo.isSuccess()) {
                                    // TODO 进入登录后界面
                                    Toast.makeText(SplashActivity.this, "登录成功，目前无跳转界面，请清空数据后体验后续流程", Toast.LENGTH_LONG).show();
                                } else {
                                    startMapActivity();
                                }
                            } else {
                                startMapActivity();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {  
                            startMapActivity();
                        }
                    },
                    false);
        }
    }
    
    private void startMapActivity() {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
    }
}
