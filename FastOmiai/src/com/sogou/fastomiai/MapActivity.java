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

import android.app.Activity;
import android.os.Bundle;

public class MapActivity extends Activity {
	
	// 默认约会地点
	final LatLng default_des1 = new LatLng(39.917478, 116.378928); // 西单
	final LatLng default_des2 = new LatLng(39.921171, 116.418094); // 王府井
	final LatLng default_des3 = new LatLng(39.940045, 116.46115); // 三里屯
	final LatLng default_des4 = new LatLng(39.959653, 116.422325); // 地坛
	final LatLng default_des5 = new LatLng(40.016262, 116.399059); // 奥林匹克森林公园
	final LatLng default_des6 = new LatLng(40.006929, 116.283177); // 北宫门
	final LatLng default_des7 = new LatLng(39.997271, 116.194793); // 香山
    
	// 基础地图
	private MapView mMapView = null;
	private static BaiduMap mBaiduMap = null;
	
	// 定位
	public LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        
        mMapView = (MapView) findViewById(R.id.bmapView);
        
	    // 开启定位图层 
	    mBaiduMap = mMapView.getMap();
	    mBaiduMap.setMyLocationEnabled(true); 
	    
	    // 发起定位
	    mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
		mLocationClient.requestLocation();
		
		// 标注默认约会地点
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.destination_icon);
		LatLng point1 = new LatLng(default_des1.latitude, default_des1.longitude);
		OverlayOptions option1 = new MarkerOptions()
			.position(point1)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option1);
		LatLng point2 = new LatLng(default_des2.latitude, default_des2.longitude);
		OverlayOptions option2 = new MarkerOptions()
			.position(point2)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option2);
		LatLng point3 = new LatLng(default_des3.latitude, default_des3.longitude);
		OverlayOptions option3 = new MarkerOptions()
			.position(point3)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option3);
		LatLng point4 = new LatLng(default_des4.latitude, default_des4.longitude);
		OverlayOptions option4 = new MarkerOptions()
			.position(point4)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option4);		
		LatLng point5 = new LatLng(default_des5.latitude, default_des5.longitude);
		OverlayOptions option5 = new MarkerOptions()
			.position(point5)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option5);
		LatLng point6 = new LatLng(default_des6.latitude, default_des6.longitude);
		OverlayOptions option6 = new MarkerOptions()
			.position(point6)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option6);
		LatLng point7 = new LatLng(default_des7.latitude, default_des7.longitude);
		OverlayOptions option7 = new MarkerOptions()
			.position(point7)
			.icon(bitmap)
			.zIndex(9)
			.draggable(false);
		mBaiduMap.addOverlay(option7);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
		mMapView.onResume();
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
				.period(10)
				.draggable(false);
			mBaiduMap.addOverlay(option);
		}
	}

	public BDLocationListener myListener = new MyLocationListener();
}
