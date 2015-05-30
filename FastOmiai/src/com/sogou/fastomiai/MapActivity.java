package com.sogou.fastomiai;

import java.util.ArrayList;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.sogou.fastomiai.controller.SessionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapActivity extends Activity {
	
	// 默认约会地点
	final LatLng default_des1 = new LatLng(39.974957, 116.37584);
	final LatLng default_des2 = new LatLng(39.974957, 116.370283);
	final LatLng default_des3 = new LatLng(39.974722, 116.436662);
	final LatLng default_des4 = new LatLng(39.906968, 116.404503);
	final LatLng default_des5 = new LatLng(39.876322, 116.332782);
	final LatLng default_des6 = new LatLng(39.850179, 116.424014);
    
	// 基础地图
	private MapView mMapView = null;
	private static BaiduMap mBaiduMap = null;
	
	// 定位
	public LocationClient mLocationClient = null;
	
	private Button mBtnRegister = null;
	private Button mBtnLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    startLoginActivity(true);
			}
		});
        mBtnLogin = (Button) findViewById(R.id.btn_loggin);
        mBtnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    startLoginActivity(false);
			}
		});
        
	    // 开启定位图层 
	    mBaiduMap = mMapView.getMap();
	    mBaiduMap.setMyLocationEnabled(true);
	    
	    // 发起定位
	    mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
		mLocationClient.requestLocation();
		
		BitmapDescriptor bitmapBlue = BitmapDescriptorFactory  
			    .fromResource(R.drawable.location_blue);
		LatLng point3 = new LatLng(default_des3.latitude, default_des3.longitude);
		OverlayOptions option3 = new MarkerOptions()
			.position(point3)
			.icon(bitmapBlue)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option3);
		LatLng point6 = new LatLng(default_des6.latitude, default_des6.longitude);
		OverlayOptions option6 = new MarkerOptions()
			.position(point6)
			.icon(bitmapBlue)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option6);
		
		BitmapDescriptor bitmapBubble1 = BitmapDescriptorFactory  
			    .fromResource(R.drawable.meeting);
		LatLng pointBubble = new LatLng(default_des1.latitude, default_des1.longitude);
		OverlayOptions optionBubble1 = new MarkerOptions()
			.position(pointBubble)
			.icon(bitmapBubble1)
			.zIndex(0)
			.draggable(false);
		mBaiduMap.addOverlay(optionBubble1);
		
		BitmapDescriptor bitmapBubble4 = BitmapDescriptorFactory  
			    .fromResource(R.drawable.confirm_to_meet);
		LatLng pointBubble4 = new LatLng(default_des4.latitude, default_des4.longitude);
		OverlayOptions optionBubble4 = new MarkerOptions()
			.position(pointBubble4)
			.icon(bitmapBubble4)
			.zIndex(0)
			.draggable(false);
		mBaiduMap.addOverlay(optionBubble4);
		
		BitmapDescriptor bitmapBubble5 = BitmapDescriptorFactory  
			    .fromResource(R.drawable.information_ok);
		LatLng pointBubble5 = new LatLng(default_des5.latitude, default_des5.longitude);
		OverlayOptions optionBubble5 = new MarkerOptions()
			.position(pointBubble5)
			.icon(bitmapBubble5)
			.zIndex(0)
			.draggable(false);
		mBaiduMap.addOverlay(optionBubble5);
    }
    
    private void startLoginActivity(boolean isRegister) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(LoginActivity.IS_REGISTER, isRegister);
        startActivity(intent);
    }
    
    @Override
	protected void onResume() {
        super.onResume();
        if (SessionManager.getInstance(this).isLogin()) {
            finish();
        } else {
            mMapView.onResume();
        }
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		mMapView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		mMapView.onDestroy();
	}
	
	

	public static class MyLocationListener implements BDLocationListener {
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			
			if (location == null)
				return;
						 
			MyLocationData locData = new MyLocationData.Builder()  
			    .accuracy(0)   
			    .direction(location.getDirection()).latitude(location.getLatitude())  
			    .longitude(location.getLongitude()).build();  
			mBaiduMap.setMyLocationData(locData);
			
			BitmapDescriptor bitmap1 = BitmapDescriptorFactory  
			    .fromResource(R.drawable.my_location_small);
			BitmapDescriptor bitmap2 = BitmapDescriptorFactory  
				    .fromResource(R.drawable.my_location_small1);
			LatLng point = new LatLng(locData.latitude, locData.longitude);
			ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
			giflist.add(bitmap1);
			giflist.add(bitmap2);
			OverlayOptions option = new MarkerOptions()
				.position(point)
				.icons(giflist)
				.zIndex(9)
				.period(60)
				.draggable(false);
			mBaiduMap.addOverlay(option);
		}
	}

	public BDLocationListener myListener = new MyLocationListener();
}
