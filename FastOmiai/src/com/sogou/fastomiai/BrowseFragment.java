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
import android.widget.Toast;

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
	public static final String EXTRA_USERID = "USERID";
    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    private BrowseActivity mContext;
    private Button mBtnNoLove = null;
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
    
    private Button mBtnUber = null;
    private Button mBtnFlower = null;
    private Button mBtnCoupon = null;
    private Button mBtnMore = null;
    private Button mBtnChatList = null;
    private Button mBtnNavigation = null;
    private Button mBtnMeeting = null;
    
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
        String strUri = mContext.getUri();
        
    	if (strID != null && strID.equals(strUser)) {
    		rootView = (ViewGroup) inflater
	                .inflate(R.layout.fragment_confirm, container, false);	
            NetworkImageView imageHead = (NetworkImageView)rootView.findViewById(R.id.image_user_photo);
            imageHead.setImageUrl(mStrUrl, NetworkRequest.getInstance(mContext.getApplicationContext()).getImageLoader());
            imageHead.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, TargetInfoActivity.class);
					intent.putExtra(EXTRA_USERID, strUser);
					intent.putExtra(TargetInfoActivity.EXTRA_UID, mFindListInfo.data.get(mPageNumber).uid);
					intent.putExtra(TargetInfoActivity.EXTRA_HEAD, mFindListInfo.data.get(mPageNumber).headUrl);
    			    startActivity(intent);
				}
			});
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
    	else if ( strUri!= null && strUri.equals(strUser)) {
    		
    		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_meeting_wait2, container, false);
    		  NetworkImageView imageHead = (NetworkImageView)rootView.findViewById(R.id.image_user_photo);
              imageHead.setImageUrl(mStrUrl, NetworkRequest.getInstance(mContext.getApplicationContext()).getImageLoader());
              imageHead.setOnClickListener(new OnClickListener() {
  				
  				@Override
  				public void onClick(View v) {
  					Intent intent = new Intent(mContext, TargetInfoActivity.class);
  					intent.putExtra(EXTRA_USERID, strUser);
      			    startActivity(intent);
  				}
  			});
              

              mBtnUber = (Button) rootView.findViewById(R.id.btn_uber_code);
              mBtnUber.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
      				Toast.makeText(mContext.getApplicationContext(), "Uber码功能尚未实现", Toast.LENGTH_SHORT).show();
      			}
      		});
              
              mBtnFlower = (Button) rootView.findViewById(R.id.btn_flower);
              mBtnFlower.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
      				Toast.makeText(mContext.getApplicationContext(), "鲜花送功能尚未实现", Toast.LENGTH_SHORT).show();
      			}
      		});
              
              mBtnCoupon = (Button) rootView.findViewById(R.id.btn_coupon);
              mBtnCoupon.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
      				Toast.makeText(mContext.getApplicationContext(), "审美券功能尚未实现", Toast.LENGTH_SHORT).show();
      			}
      		});
              
              mBtnMore = (Button) rootView.findViewById(R.id.btn_more);
              mBtnMore.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
      				Toast.makeText(mContext.getApplicationContext(), "更多", Toast.LENGTH_SHORT).show();
      			}
      		});
              
              mBtnChatList = (Button) rootView.findViewById(R.id.btn_chat_list);
              mBtnChatList.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
      				Toast.makeText(mContext.getApplicationContext(), "私信", Toast.LENGTH_SHORT).show();
      			}
      		});
              
              mBtnNavigation = (Button)rootView.findViewById(R.id.btn_navigation);
              mBtnNavigation.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
      				Toast.makeText(mContext.getApplicationContext(), "地点导航功能尚未实现", Toast.LENGTH_SHORT).show();
      			}
      		});
              
              mBtnMeeting = (Button) rootView.findViewById(R.id.btn_meeting);
              mBtnMeeting.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v) {
    				Intent intent = new Intent(mContext.getApplicationContext(), MeetingActivity.class);
    				startActivity(intent);      			}
      		});
             
    		
		}
    	else {
    		rootView = (ViewGroup) inflater
    	                .inflate(R.layout.fragment_browse, container, false);	
            NetworkImageView imageHead = (NetworkImageView)rootView.findViewById(R.id.image_user_photo);
            imageHead.setImageUrl(mStrUrl, NetworkRequest.getInstance(mContext.getApplicationContext()).getImageLoader());
            imageHead.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, TargetInfoActivity.class);
					intent.putExtra(EXTRA_USERID, strUser);
                    intent.putExtra(TargetInfoActivity.EXTRA_UID, mFindListInfo.data.get(mPageNumber).uid);
                    intent.putExtra(TargetInfoActivity.EXTRA_HEAD, mFindListInfo.data.get(mPageNumber).headUrl);
    			    startActivity(intent);
				}
			});
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
            mBtnNoLove = (Button) rootView.findViewById(R.id.btn_no_love);
            if (mBtnNoLove != null) {
            	mBtnNoLove.setOnClickListener(new OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				Toast.makeText(mContext, "该用户不会再被推荐", Toast.LENGTH_SHORT).show();
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
