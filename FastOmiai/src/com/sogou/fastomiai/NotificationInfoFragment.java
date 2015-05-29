package com.sogou.fastomiai;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class NotificationInfoFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    private Context mContext;
    private Button mBtnRegret = null;
    private Button mBtnAgree = null;
    
    boolean[] mPlaces = {false, false, false, false, false};

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static NotificationInfoFragment create(Context context, int pageNumber) {
    	NotificationInfoFragment fragment = new NotificationInfoFragment(context);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationInfoFragment(Context context) {
    	mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE); 
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_notification_info, container, false);
        
        mBtnRegret = (Button) rootView.findViewById(R.id.btn_regret);
        mBtnRegret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "残忍地拒绝", Toast.LENGTH_SHORT).show();
			}
		});
        
        mBtnAgree = (Button) rootView.findViewById(R.id.btn_agree);
        mBtnAgree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PlacePickerActivity.class);
				intent.putExtra("places", mPlaces);
			    startActivity(intent);
			}
		});

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
