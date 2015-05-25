package com.sogou.fastomiai.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    private static final String APP_PREFERENCE = "app_preference";
    
    private static final String LOGGED_ID = "logged_id";
    
    public static String getLoggedID(Context context) {
        return getString(context, LOGGED_ID);
    }
    
    public static void setLoggedID(Context context, String s) {
        saveString(context, LOGGED_ID, s);
    }
    
    private static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }

    private static void saveBoolean(Context context, String key, boolean b) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(key, b);
        editor.commit();
    }

    private static boolean getBoolean(Context context, String key) {
        return getPreferences(context).getBoolean(key, false);
    }

    private static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPreferences(context).getBoolean(key, defaultValue);
    }

    private static void saveLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    private static long getLong(Context context, String key) {
        return getPreferences(context).getLong(key, System.currentTimeMillis());
    }

    private static long getLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSelfPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
    }
}
