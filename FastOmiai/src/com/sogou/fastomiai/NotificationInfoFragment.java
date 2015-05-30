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
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.sogou.fastomiai.model.InviteItemInfo;
import com.sogou.fastomiai.model.InviteItemInfo.SexEnum;
import com.sogou.fastomiai.util.NetworkRequest;

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
    private int mInviteID;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static NotificationInfoFragment create(Context context, int pageNumber, boolean[] places, int inviteID) {
    	NotificationInfoFragment fragment = new NotificationInfoFragment(context);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putBooleanArray("places", places);
        args.putInt("inviteid", inviteID);
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
        mPlaces = getArguments().getBooleanArray("places");
        mInviteID = getArguments().getInt("inviteid");
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
        
        final InviteItemInfo inviteInfo = ((NotificationInfoActivity)getActivity()).getInviteInfo();
        
        mBtnAgree = (Button) rootView.findViewById(R.id.btn_agree);
        mBtnAgree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PlacePickerActivity.class);
				intent.putExtra("places", mPlaces);
				intent.putExtra("inviteid", mInviteID);
				if (inviteInfo != null && inviteInfo.data != null 
				        && inviteInfo.data.user != null && inviteInfo.data.user.sex != null) {
				    intent.putExtra("ismale", (inviteInfo.data.user.sex == SexEnum.SEX_MALE));
				}
			    startActivity(intent);
			}
		});
        
        if (inviteInfo != null) {
            NetworkImageView headImage = (NetworkImageView) rootView
                    .findViewById(R.id.image_user_photo);
            headImage.setImageUrl(inviteInfo.data.user.headUrl, NetworkRequest
                    .getInstance(getActivity().getApplicationContext())
                    .getImageLoader());
            
            Button btnTag1 = (Button)rootView.findViewById(R.id.text_tag1);
            Button btnTag2 = (Button)rootView.findViewById(R.id.text_tag2);
            Button btnTag3 = (Button)rootView.findViewById(R.id.text_tag3);
            Button btnTag4 = (Button)rootView.findViewById(R.id.text_tag4);
            Button btnTag5 = (Button)rootView.findViewById(R.id.text_tag5);
            
            ArrayList<String> tags = inviteInfo.data.user.tags;
            if (tags != null) {
                switch(tags.size()) {
                case 5:
                    btnTag5.setVisibility(View.VISIBLE);
                case 4:
                    btnTag4.setVisibility(View.VISIBLE);
                case 3:
                    btnTag3.setVisibility(View.VISIBLE);
                case 2:
                    btnTag2.setVisibility(View.VISIBLE);
                case 1:
                    btnTag1.setVisibility(View.VISIBLE);
                default:
                    break;
                }
            }
        }

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
