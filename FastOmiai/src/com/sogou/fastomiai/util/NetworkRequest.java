package com.sogou.fastomiai.util;

import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.pushclient.ServiceManager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sogou.fastomiai.R;

public class NetworkRequest {
    private volatile static NetworkRequest sInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context sContext;
    
    private boolean isPushServiceStarted = false;
    
    private NetworkRequest(Context context) {
        sContext = context;
        mRequestQueue = getRequestQueue();
        
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                cache = new LruCache<String, Bitmap>(20);
            
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
            
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }
    
    public static NetworkRequest getInstance(Context text) {
        if (sInstance == null) {
            synchronized (NetworkRequest.class) {
                if (sInstance == null) {
                    sInstance = new NetworkRequest(text);                    
                }
            }
        }
        return sInstance;
    }
    
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static <T> Request<T> get(String url, Class<T> clazz,
            Response.Listener<T> listener,
            Response.ErrorListener errorListener, boolean needUrlDecode) {

        RequestQueue queue = sInstance.getRequestQueue();
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener,
                errorListener, needUrlDecode);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, 2.f));
        return queue.add(request);
    }
    
    public static <T> Request<T> post(String url,
            final Map<String, String> params, Class<T> clazz,
            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = sInstance.getRequestQueue();
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, url, clazz, listener,
                errorListener, false) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        return queue.add(request);
    }
    
    public void startPushService() {
        if (!isPushServiceStarted) {
            ServiceManager serviceManager = new ServiceManager(sContext.getApplicationContext());
            serviceManager.setNotificationIcon(R.drawable.notification);
            serviceManager.startService();
            isPushServiceStarted = true;
        }
    }
}
