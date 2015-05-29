package com.sogou.fastomiai;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;

public class MapChoosePlaceActivity extends Activity {

	private ImageButton mBtnBack = null;
	private Button mBtnDate = null;
	
	private CheckBox mCheckPlace1 = null;
	private CheckBox mCheckPlace2 = null;
	private CheckBox mCheckPlace3 = null;
	private CheckBox mCheckPlace4 = null;
	private CheckBox mCheckPlace5 = null;
	
	boolean[] mPlaces = {false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_place);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_choose_place_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
			    startActivity(intent);
			}
		});
        
        mBtnDate = (Button) findViewById(R.id.btn_choose);
        mBtnDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (3 > getPlacesCount()) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.choose_none_hint),
							Toast.LENGTH_SHORT).show();
				}
				else {
					Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
				    startActivity(intent);
				}				
			}
		});
        
        mCheckPlace1 = (CheckBox) findViewById(R.id.check_place1);
        mCheckPlace1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && (2 < getPlacesCount())) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.choose_over_hint),
							Toast.LENGTH_SHORT).show();
					mCheckPlace1.setChecked(false);
					return;
				}
				else {
					mPlaces[0] = isChecked;
				}				
			}
		});
        
        mCheckPlace2 = (CheckBox) findViewById(R.id.check_place2);
        mCheckPlace2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && (2 < getPlacesCount())) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.choose_over_hint),
							Toast.LENGTH_SHORT).show();
					mCheckPlace2.setChecked(false);
					return;
				}
				else {
					mPlaces[1] = isChecked;
				}				
			}
		});
        
        mCheckPlace3 = (CheckBox) findViewById(R.id.check_place3);
        mCheckPlace3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && (2 < getPlacesCount())) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.choose_over_hint),
							Toast.LENGTH_SHORT).show();
					mCheckPlace3.setChecked(false);
					return;
				}
				else {
					mPlaces[2] = isChecked;
				}				
			}
		});
        
        mCheckPlace4 = (CheckBox) findViewById(R.id.check_place4);
        mCheckPlace4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && (2 < getPlacesCount())) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.choose_over_hint),
							Toast.LENGTH_SHORT).show();
					mCheckPlace4.setChecked(false);
					return;
				}
				else {
					mPlaces[3] = isChecked;
				}				
			}
		});
        
        mCheckPlace5 = (CheckBox) findViewById(R.id.check_place5);
        mCheckPlace5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && (2 < getPlacesCount())) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.choose_over_hint),
							Toast.LENGTH_SHORT).show();
					mCheckPlace5.setChecked(false);
					return;
				}
				else {
					mPlaces[4] = isChecked;
				}				
			}
		});
    }
    
    private int getPlacesCount() {
    	int nCount = 0;
    	for (int i = 0; i < mPlaces.length; i++) {
    	     if (mPlaces[i]) {
    	    	 nCount++;
    	     }
    	}
    	return nCount;
    }

}
