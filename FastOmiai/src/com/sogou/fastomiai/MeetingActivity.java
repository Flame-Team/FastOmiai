package com.sogou.fastomiai;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.libra.sinvoice.LogHelper;
import com.libra.sinvoice.SinVoicePlayer;
import com.libra.sinvoice.SinVoiceRecognition;
import com.sogou.fastomiai.controller.SessionManager;
import com.sogou.fastomiai.model.InviteFinishInfo;
import com.sogou.fastomiai.util.Constants;
import com.sogou.fastomiai.util.NetworkRequest;
import com.sogou.fastomiai.util.NetworkUtil;

public class MeetingActivity extends Activity implements SinVoiceRecognition.Listener,
SinVoicePlayer.Listener {
    private final static String TAG = "MeetingActivity";
    
    private final static int MAX_NUMBER = 5;
    private final static int MSG_SET_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_RECG_END = 3;

    private final static String CODEBOOK = "12345";

    private Handler mHandler;
    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;
    
    private String mText;
    
    private TextView mTextTip;
    private TextView mTextGift;
    
    private final static int MSG_LENGTH = 7;
    private int mCountDown = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        mSinVoicePlayer = new SinVoicePlayer(CODEBOOK);
        mSinVoicePlayer.setListener(this);

        mRecognition = new SinVoiceRecognition(CODEBOOK);
        mRecognition.setListener(this);
        
        mRecognition.start();
        
        mText = genText(7);
        mSinVoicePlayer.play(mText, true, 1000);

        mHandler = new Handler() {
            private StringBuilder mTextBuilder = new StringBuilder();
            private boolean isOneMoreTime = false;
            
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case MSG_SET_RECG_TEXT:
                    char ch = (char) msg.arg1;
                    mTextBuilder.append(ch);
                    break;

                case MSG_RECG_START:
                    mTextBuilder.delete(0, mTextBuilder.length());
                    break;

                case MSG_RECG_END:
                    if (isOneMoreTime) {
                        if (mCountDown <= 0) {
                            mCountDown--;
                            meetingSuccess();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), mTextBuilder.toString(), Toast.LENGTH_SHORT).show();
                        if (mTextBuilder.length() < MSG_LENGTH) {
                            isOneMoreTime = true;
                        }
                    }
                    LogHelper.d(TAG, "recognition end");
                    break;
                }
                super.handleMessage(msg);
            }
        };
        
        mTextTip = (TextView)findViewById(R.id.meeting_tip);
        mTextGift = (TextView)findViewById(R.id.meeting_gift);
    }
    
    private void meetingSuccess() {
        mRecognition.stop();
        mSinVoicePlayer.stop();
        Toast.makeText(getApplicationContext(), "确认见面成功", Toast.LENGTH_SHORT).show();
        mTextGift.setVisibility(View.VISIBLE);
        mTextTip.setText(R.string.meeting_tip_success);
        
        SessionManager sm = SessionManager.getInstance(this);
        String token = sm.getToken();
        if (token.isEmpty()) {
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.TOKEN, token);
            String url = NetworkUtil.getUrl(Constants.INVITE_FINISH_URL, params);
            NetworkRequest.get(url, InviteFinishInfo.class,
                    new Response.Listener<InviteFinishInfo>() {
                        @Override
                        public void onResponse(InviteFinishInfo info) {
                            if (info != null) {
                                if (info.isSuccess()) {
                                } else {
                                }
                            } else {
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {  
                        }
                    },
                    false);
        }
        
    }

    private String genText(int count) {
        StringBuilder sb = new StringBuilder();
        int pre = 0;
        while (count > 0) {
            int x = (int) (Math.random() * MAX_NUMBER + 1);
            if (Math.abs(x - pre) > 0) {
                sb.append(x);
                --count;
                pre = x;
            }
        }

        return sb.toString();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mRecognition.stop();
        mSinVoicePlayer.stop();
    }

    @Override
    public void onRecognitionStart() {
        mHandler.sendEmptyMessage(MSG_RECG_START);
    }

    @Override
    public void onRecognition(char ch) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
    }

    @Override
    public void onRecognitionEnd() {
        mHandler.sendEmptyMessage(MSG_RECG_END);
    }

    @Override
    public void onPlayStart() {
        LogHelper.d(TAG, "start play");
    }

    @Override
    public void onPlayEnd() {
        LogHelper.d(TAG, "stop play");
    }
}
