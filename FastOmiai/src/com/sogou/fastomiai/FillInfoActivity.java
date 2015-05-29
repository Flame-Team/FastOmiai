package com.sogou.fastomiai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.InviteItemInfo.SexEnum;
import com.sogou.fastomiai.model.UserSuppInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;


public class FillInfoActivity extends FragmentActivity {

    private ImageButton mBtnBack = null;
    private TextView mTextTitle = null;
    private Button mBtnNext = null;
    
    private FragmentManager fragmentManager = null;
    private FillBaseInfoFragment mTab01;
    private FillPhotoFragment mTab02;
    private FillDesireFragment mTab03;
    private int mIndex = 0;
    
    private UserSupplementInfo mUserSupplementInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_info);
        
        mUserSupplementInfo = new UserSupplementInfo();
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_login_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mBtnNext.setText(getResources().getString(R.string.fill_info_next));
				if (1 == mIndex) {
					mBtnBack.setVisibility(View.INVISIBLE);
				}
				setTabSelection(mIndex - 1);
			}
		});
        
        mTextTitle = (TextView) findViewById(R.id.text_fill_info_title);
        
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (mIndex) {
                case 1:
//                    if (mTab02 != null && !mTab02.isFilled()) {
//                        Toast.makeText(getApplicationContext(), "请上传照片",
//                                Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    mBtnNext.setText(getResources().getString(
                            R.string.fill_info_finish));
                    mBtnBack.setVisibility(View.VISIBLE);
                    setTabSelection(mIndex + 1);
                    break;
				case 0:
                    if (mTab01 != null && !mTab01.isFilled()) {
                        Toast.makeText(getApplicationContext(), "请补全资料",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
					mBtnBack.setVisibility(View.VISIBLE);
					setTabSelection(mIndex + 1);
					break;
				case 2:
				    commitUserInfo();
				    break;
				default:
					break;
				}
			}
		});
        
        fragmentManager = getSupportFragmentManager();
        setTabSelection(mIndex);

    }
    
    void commitUserInfo() {
        mTab01.fillInfo(mUserSupplementInfo);
        mTab02.fillInfo(mUserSupplementInfo);
        mTab03.fillInfo(mUserSupplementInfo);
        
        String token = SessionManager.getInstance(getApplicationContext()).getToken();
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.TOKEN, token);
        
        String url = NetworkUtil.getUrl(Constants.USER_SUPP_URL, params);

        params = mUserSupplementInfo.toKeyValue();

        NetworkRequest.post(url, params, UserSuppInfo.class,
                new Response.Listener<UserSuppInfo>() {
                    @Override
                    public void onResponse(UserSuppInfo suppInfo) {
                        if (suppInfo != null && suppInfo.isSuccess()) {
                            Toast.makeText(getApplicationContext(), "提交成功",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),
                                    BrowseActivity.class);
                            startActivity(intent);
                        } else {
                            if (suppInfo != null && suppInfo.errmsg != null) {
                                Toast.makeText(getApplicationContext(),
                                        suppInfo.errmsg, Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                Toast.makeText(getApplicationContext(), "提交失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "提交失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    void setTabSelection(int index) {
    	
    	if (0 > index || 2 < index) {
    		return;
    	}
    	
    	mIndex = index;
    	mTextTitle.setText(getResources().getString(R.string.fill_info_title) + "(" + (mIndex + 1) + "/3)");
    	
    	FragmentTransaction transaction = fragmentManager.beginTransaction();
    	hideFragments(transaction);
    	switch (index) {
	    	case 0:
	    		if (null == mTab01) {
	    			mTab01 = new FillBaseInfoFragment();
	        		transaction.add(R.id.fragment_container, mTab01);
	        	}
	    		else {
	    			transaction.show(mTab01);
	    		}
	    		break;
	    	case 1:
	    		if (null == mTab02) {
	    			mTab02 = new FillPhotoFragment();
	        		transaction.add(R.id.fragment_container, mTab02);
	        	}
	    		else {
	    			transaction.show(mTab02);
	    		}
	    		break;
	    	case 2:
	    		if (null == mTab03) {
	    			mTab03 = new FillDesireFragment();
	        		transaction.add(R.id.fragment_container, mTab03);
	        	}
	    		else {
	    			transaction.show(mTab03);
	    		}
	    		break;
    		default:
    			break;
    	}
    	
    	transaction.commit();
    	
    }
    
    private void hideFragments(FragmentTransaction transaction) {
    	if (null != mTab01) {
    		transaction.hide(mTab01);
    	}
    	if (null != mTab02) {
    		transaction.hide(mTab02);
    	}
    	if (null != mTab03) {
    		transaction.hide(mTab03);
    	}
    }
    
    public static class UserSupplementInfo {
        @SerializedName("name")
        public String name;
        
        @SerializedName("sex")
        public SexEnum sex;
        
        @SerializedName("birth")
        public int age;
        
        @SerializedName("height")
        public int height = 0;

        @SerializedName("income")
        public IncomeEnum income;
        
        @SerializedName("is_car")
        public ZeroOrOneEnum hasCar;
        
        @SerializedName("is_house")
        public ZeroOrOneEnum hasHouse;
        
        @SerializedName("edu")
        public EducationEnum edu;
        
        @SerializedName("level")
        public LevelEnum level;
        
        @SerializedName("marriage")
        public MarriageEnum marriage;
        
        @SerializedName("photo")
        public ArrayList<FileItem> photo = null;
        
        @SerializedName("vedio")
        public ArrayList<FileItem> vedio = null;
        
        public Map<String, String> toKeyValue() {
            Map<String, String> mapInfo = new HashMap<String, String>();
            Gson builder = new GsonBuilder().create();
            
            mapInfo.put("name", name);
            mapInfo.put("sex", sex.toString());
            mapInfo.put("birth", String.valueOf(age));
            mapInfo.put("height", String.valueOf(height));
            mapInfo.put("income", income.toString());
            mapInfo.put("is_car", hasCar.toString());
            mapInfo.put("is_house", hasHouse.toString());
            mapInfo.put("edu", edu.toString());
            mapInfo.put("level", level.toString());
            mapInfo.put("marriage", marriage.toString());
            mapInfo.put("photo", builder.toJson(photo));
            mapInfo.put("vedio", builder.toJson(vedio));
            
            return mapInfo;
        }
    }
    
    public static enum IncomeEnum {
        @SerializedName("1")
        INCOME_BELOW_2K,
        
        @SerializedName("2")
        INCOME_2K_TO_5K,
        
        @SerializedName("3")
        INCOME_5K_TO_1W,
        
        @SerializedName("4")
        INCOME_1W_TO_2W,
        
        @SerializedName("5")
        INCOME_ABOVE_2W;
        
        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }
    
    public static enum ZeroOrOneEnum {
        @SerializedName("0")
        ENUM_ZERO,
        
        @SerializedName("1")
        ENUM_ONE;
        
        @Override
        public String toString() {
            return String.valueOf(ordinal());
        }
    }
    
    public static enum EducationEnum {
        @SerializedName("1")
        EDU_HIGH_SCHOOL,
        
        @SerializedName("2")
        EDU_JUNIOR_COLLEGE,
        
        @SerializedName("3")
        EDU_BACHELOR,
        
        @SerializedName("4")
        EDU_MASTER,
        
        @SerializedName("5")
        EDU_PHD;
        
        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }
    
    public static enum LevelEnum {
        @SerializedName("1")
        LEVEL_1,
        
        @SerializedName("2")
        LEVEL_2,
        
        @SerializedName("3")
        LEVEL_3,
        
        @SerializedName("4")
        LEVEL_4,
        
        @SerializedName("5")
        LEVEL_5;
        
        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }
    
    public static enum MarriageEnum {
        @SerializedName("1")
        UNMARRIED,
        
        @SerializedName("2")
        DIVORCE,
        
        @SerializedName("3")
        WIDOWED;
        
        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }
    
    public static class FileItem {
        @SerializedName("name")
        public String name = null;
        
        @SerializedName("file")
        public String file = null;
    }
}
