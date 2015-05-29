package com.sogou.fastomiai;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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
    
    private Context mContext;
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
    
    private NetworkImageView netImageView; 
    
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static BrowseFragment create(Context context, int pageNumber, FindListInfo findListInfo) {
    	BrowseFragment fragment = new BrowseFragment(context, findListInfo);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        
        return fragment;
    }

    public BrowseFragment(Context context, FindListInfo findListInfo) {
    	mContext = context;
    	mFindListInfo = findListInfo;
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
                .inflate(R.layout.fragment_browse, container, false);
        
        if (mFindListInfo.data.size() <= mPageNumber) {
			return null;
		}
        
        mStrUrl = mFindListInfo.data.get(mPageNumber).headUrl;
        mListTags = mFindListInfo.data.get(mPageNumber).tags;
        
        NetworkImageView imageHead = (NetworkImageView)rootView.findViewById(R.id.image_user_photo);
        imageHead.setImageUrl(mStrUrl, NetworkRequest.getInstance(mContext.getApplicationContext()).getImageLoader());
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
        mBtnDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, MapChoosePlaceActivity.class);
			    startActivity(intent);
			}
		});

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
    

    

}
