package com.sogou.fastomiai;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.UserLoginInfo;
import com.sogou.fastomiai.model.UserRegInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;


public class LoginActivity extends Activity {
    public static final String IS_REGISTER = "is_register";
    
    private ImageButton mBtnBack = null;
    private Button mBtnActFinish = null;
    private EditText mEditTel = null;
    private EditText mEditCode = null;
    
    
    private boolean isRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Intent intent = getIntent();
        if (intent != null) {            
            isRegister = intent.getBooleanExtra(IS_REGISTER, false);
        }
        
        TextView tvLoginTitle = (TextView)findViewById(R.id.text_login_title);
        TextView tvAccountHint = (TextView)findViewById(R.id.text_account_hint);
        TextView tvTelHint = (TextView)findViewById(R.id.text_tel_hint);
        if (isRegister) {
            tvLoginTitle.setText(R.string.register);
            tvAccountHint.setText(R.string.register_account_hint);
            tvTelHint.setText(R.string.register_tel_hint);
        } else {
            tvLoginTitle.setText(R.string.login);
            tvAccountHint.setText(R.string.login_account_hint);
            tvTelHint.setText(R.string.login_tel_hint);
        }
        
        mEditTel = (EditText)findViewById(R.id.edit_tel);
        mEditCode = (EditText)findViewById(R.id.edit_code);
        mEditCode.setOnFocusChangeListener(new OnFocusChangeListener() {
            
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Random rand = new Random(System.currentTimeMillis());
                    int code = 1000 + rand.nextInt(9000);
                    mEditCode.setText(String.valueOf(code));
                }
            }
        });
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_login_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MapActivity.class);
			    startActivity(intent);
			}
		});
        
        mBtnActFinish = (Button)findViewById(R.id.btn_act_finish);
        mBtnActFinish.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                String phone = mEditTel.getText().toString();
                if (phone == null || phone.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                
                String code = mEditCode.getText().toString();
                if (code == null || code.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.PHONE, phone);
                params.put(Constants.VERIFY, code);
                if (isRegister) {
                    String url = NetworkUtil.getUrl(Constants.USER_REG_URL, params);
                    NetworkRequest.get(url, UserRegInfo.class, 
                            new Response.Listener<UserRegInfo>() {
                                @Override
                                public void onResponse(UserRegInfo regInfo) {
                                    if (regInfo == null || regInfo.token == null || 
                                            regInfo.token.isEmpty()) {
                                        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        SessionManager.getInstance(LoginActivity.this)
                                            .setToken(regInfo.token);
                                        
                                        Intent intent = new Intent(getApplicationContext(), FillInfoActivity.class);
                        			    startActivity(intent);
                        			    finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }, 
                            false);
                } else {
                    String url = NetworkUtil.getUrl(Constants.USER_LOGIN_URL, params);
                    NetworkRequest.get(url, UserLoginInfo.class, 
                            new Response.Listener<UserLoginInfo>() {
                                @Override
                                public void onResponse(UserLoginInfo loginInfo) {
                                    if (loginInfo == null || loginInfo.token == null ||
                                            loginInfo.token.isEmpty()) {
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        SessionManager.getInstance(LoginActivity.this)
                                            .setToken(loginInfo.token);
                                        
                                        Intent intent = new Intent(getApplicationContext(), FillInfoActivity.class);
                        			    startActivity(intent);
                        			    finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                }
                            }, 
                            false);
                }
            }
        });
    }
    
    @Override
    protected void onResume() {
        if (SessionManager.getInstance(this).isLogin()) {
            finish();
        } else {
            super.onResume();
        }
    }
}
