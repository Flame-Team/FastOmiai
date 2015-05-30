package com.sogou.fastomiai;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.sogou.fastomiai.model.FindListInfo;
import com.sogou.fastomiai.util.NetworkRequest;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class BrowseFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    private BrowseActivity mContext;
    private Button mBtnDate = null;
    private ArrayList<Button> listBtn = new ArrayList<Button>();
    private Button mBtnTag1 = null;
    private Button mBtnTag2 = null;
    private Button mBtnTag3 = null;
    private Button mBtnTag4 = null;
    private Button mBtnTag5 = null;
    private ArrayList<String> mListTags;
    private String mStrUrl;
    private FindListInfo mFindListInfo;
    private String strUser;
    private NetworkImageView netImageView; 
	private final int[] imageIds = {R.drawable.a, R.drawable.b,
			R.drawable.c, R.drawable.d,
			R.drawable.e, R.drawable.f,
			R.drawable.g, R.drawable.h,
			R.drawable.i, R.drawable.j,
	};
	static private long sTotalTime = 60 * 60 * 24;
	private TextView mTextCountDown = null;
    static private Timer mTimer = null; 
	
    private Button mBtnHomePage = null;
    private Button mBtnFilter = null; 
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static BrowseFragment create(BrowseActivity context, int pageNumber, FindListInfo findListInfo) {
    	BrowseFragment fragment = new BrowseFragment(context, findListInfo);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        
        return fragment;
    }

    public BrowseFragment(BrowseActivity context, FindListInfo findListInfo) {
    	mContext = context;
    	mFindListInfo = findListInfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        if (null != savedInstanceState) {
        	sTotalTime = savedInstanceState.getLong("time", sTotalTime);
        } 
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
     
        if (mFindListInfo.data.size() <= mPageNumber) {
			return null;
		}
        ViewGroup rootView = null;
        
        mStrUrl = mFindListInfo.data.get(mPageNumber).headUrl;
        mListTags = mFindListInfo.data.get(mPageNumber).tags;
        strUser =  mFindListInfo.data.get(mPageNumber).uid;
        
        String strID = mContext.getID();
    	if (strID != null && strID.equals(strUser)) {
    		rootView = (ViewGroup) inflater
	                .inflate(R.layout.fragment_confirm, container, false);	
            NetworkImageView imageHead = (NetworkImageView)rootView.findViewById(R.id.image_user_photo);
            imageHead.setImageUrl(mStrUrl, NetworkRequest.getInstance(mContext.getApplicationContext()).getImageLoader());
            //imageHead.setBackgroundResource(imageIds[mPageNumber]);
            mBtnTag1 = (Button) rootView.findViewById(R.id.text_tag1);
            mBtnTag2 = (Button) rootView.findViewById(R.id.text_tag2);
            mBtnTag3 = (Button) rootView.findViewById(R.id.text_tag3);
            mBtnTag4 = (Button) rootView.findViewById(R.id.text_tag4);
            mBtnTag5 = (Button) rootView.findViewById(R.id.text_tag5);
            listBtn.add(mBtnTag1);
            listBtn.add(mBtnTag2);
            listBtn.add(mBtnTag3);
            listBtn.add(mBtnTag4);
            listBtn.add(mBtnTag5);
            for(int i=0; i<mListTags.size(); i++)
            {
            	listBtn.get(i).setText(mListTags.get(i));
            	listBtn.get(i).setVisibility(View.VISIBLE);
            }
            
            mTextCountDown = (TextView) rootView.findViewById(R.id.text_countdown);
            if (null != mTimer) {
            	mTimer.cancel();
            }        
            mTimer = new Timer();
            TimerTask task = new TimerTask() {    
                @Override    
                public void run() {    
           
                   mContext.runOnUiThread(new Runnable() {  
                        @Override    
                        public void run() {    
                        	sTotalTime--;
                            mTextCountDown.setText(mContext.getString(R.string.confirm_wait) + "\n" +
                            		sTotalTime / (60 * 60) + ":" +
                            		(sTotalTime % (60 * 60)) / 60 + ":" + 
                            		((sTotalTime % (60 * 24)) % 60));    
                            if(sTotalTime < 0){    
                            	mTimer.cancel();
                            }    
                        }    
                    });    
                }    
            }; 
            mTimer.schedule(task, 0, 1000);
            
		}
    	else {
    		rootView = (ViewGroup) inflater
    	                .inflate(R.layout.fragment_browse, container, false);	
            NetworkImageView imageHead = (NetworkImageView)rootView.findViewById(R.id.image_user_photo);
            imageHead.setImageUrl(mStrUrl, NetworkRequest.getInstance(mContext.getApplicationContext()).getImageLoader());
            //imageHead.setBackgroundResource(imageIds[mPageNumber]);
            mBtnTag1 = (Button) rootView.findViewById(R.id.text_tag1);
            mBtnTag2 = (Button) rootView.findViewById(R.id.text_tag2);
            mBtnTag3 = (Button) rootView.findViewById(R.id.text_tag3);
            mBtnTag4 = (Button) rootView.findViewById(R.id.text_tag4);
            mBtnTag5 = (Button) rootView.findViewById(R.id.text_tag5);
            listBtn.add(mBtnTag1);
            listBtn.add(mBtnTag2);
            listBtn.add(mBtnTag3);
            listBtn.add(mBtnTag4);
            listBtn.add(mBtnTag5);
            for(int i=0; i<mListTags.size(); i++)
            {
            	listBtn.get(i).setText(mListTags.get(i));
            	listBtn.get(i).setVisibility(View.VISIBLE);
            }
            
            mBtnDate = (Button) rootView.findViewById(R.id.btn_browse_date);
            if (mBtnDate != null) {
                mBtnDate.setOnClickListener(new OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				Intent intent = new Intent(mContext, MapChoosePlaceActivity.class);
        				intent.putExtra("USERID", strUser);
        			    startActivity(intent);
        			}
        		});
    		}
		}
        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
    
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("time", sTotalTime);
	}
    

}
