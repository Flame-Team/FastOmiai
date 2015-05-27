package com.sogou.fastomiai.util;

public class Constants {
    private Constants() {}
    
    // HTTP 接口及参数
    public static final String ROOT_URL = "http://123.57.132.203:8080/?method=";
    public static final String USER_AUTH_URL = ROOT_URL + "user.auth";
    public static final String USER_REG_URL = ROOT_URL + "user.reg";
    public static final String USER_LOGIN_URL = ROOT_URL + "user.login";    
    
    public static final String TOKEN = "token";
    public static final String VERIFY = "verify";
    public static final String PHONE = "phone";
}
