package com.sogou.fastomiai;

import com.sogou.fastomiai.FillInfoActivity.LevelEnum;
import com.sogou.fastomiai.FillInfoActivity.UserSupplementInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class FillDesireFragment extends Fragment {
    
    private int levelPosition = 2;
	
	private Button mImageDesire01 = null;
	private Button mImageDesire02 = null;
	private Button mImageDesire03 = null;
	private Button mImageDesire04 = null;
	private Button mImageDesire05 = null;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_fill_desire, container, false);
		init(v);
		return v;
	}
	
	private void init(View v) {
		mImageDesire01 = (Button) v.findViewById(R.id.image_desire1);
		mImageDesire01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    levelPosition = 0;
				mImageDesire02.setBackgroundResource(R.drawable.desire_unchecked_shape);
				mImageDesire03.setBackgroundResource(R.drawable.desire_unchecked_shape);
				mImageDesire04.setBackgroundResource(R.drawable.desire_unchecked_shape);
				mImageDesire05.setBackgroundResource(R.drawable.desire_unchecked_shape);
			}
		});
		mImageDesire02 = (Button) v.findViewById(R.id.image_desire2);
		mImageDesire02.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    levelPosition = 1;
				mImageDesire02.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire03.setBackgroundResource(R.drawable.desire_unchecked_shape);
				mImageDesire04.setBackgroundResource(R.drawable.desire_unchecked_shape);
				mImageDesire05.setBackgroundResource(R.drawable.desire_unchecked_shape);
			}
		});
		mImageDesire03 = (Button) v.findViewById(R.id.image_desire3);
		mImageDesire03.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    levelPosition = 2;
				mImageDesire02.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire03.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire04.setBackgroundResource(R.drawable.desire_unchecked_shape);
				mImageDesire05.setBackgroundResource(R.drawable.desire_unchecked_shape);
			}
		});
		mImageDesire04 = (Button) v.findViewById(R.id.image_desire4);
		mImageDesire04.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    levelPosition = 3;
				mImageDesire02.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire03.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire04.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire05.setBackgroundResource(R.drawable.desire_unchecked_shape);
			}
		});
		mImageDesire05 = (Button) v.findViewById(R.id.image_desire5);
		mImageDesire05.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    levelPosition = 4;
				mImageDesire02.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire03.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire04.setBackgroundResource(R.drawable.desire_checked_shape);
				mImageDesire05.setBackgroundResource(R.drawable.desire_checked_shape);
			}
		});
	}
    
   public void fillInfo(UserSupplementInfo info) {
       info.level = LevelEnum.values()[levelPosition];
   }
}
