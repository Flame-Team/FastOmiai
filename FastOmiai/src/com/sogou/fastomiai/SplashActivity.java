package com.sogou.fastomiai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.libra.sinvoice.SinVoicePlayer;
import com.libra.sinvoice.SinVoiceRecognition;


public class SplashActivity extends Activity implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {
    
    private final long SPLASH_TIME_OUT = 1000L;
    
    private Button mBtnShowMap = null;
    
    private TextView mRecognisedTextView;
    private final static int MAX_NUMBER = 5;
    private final static int MSG_SET_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_RECG_END = 3;

    private final static String CODEBOOK = "12345";

    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;

    protected Handler mHandler = new Handler () {

        private StringBuilder mTextBuilder = new StringBuilder();

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_SET_RECG_TEXT:
                char ch = (char) msg.arg1;
                mTextBuilder.append(ch);
                if (null != mRecognisedTextView) {
                    mRecognisedTextView.setText(mTextBuilder.toString());
                }
                break;

            case MSG_RECG_START:
                mTextBuilder.delete(0, mTextBuilder.length());
                break;

            case MSG_RECG_END:
                break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        mBtnShowMap = (Button) findViewById(R.id.btn_show_map);
        mBtnShowMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MapActivity.class);
			    startActivity(intent);
			}
		});
        
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startHomePage();
            }
        }, SPLASH_TIME_OUT);

        mSinVoicePlayer = new SinVoicePlayer(CODEBOOK);
        mSinVoicePlayer.setListener(this);

        mRecognition = new SinVoiceRecognition(CODEBOOK);
        mRecognition.setListener(this);

        final TextView playTextView = (TextView) findViewById(R.id.playtext);
        mRecognisedTextView = (TextView) findViewById(R.id.regtext);

        Button playStart = (Button) this.findViewById(R.id.start_play);
        playStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = genText(7);
                playTextView.setText(text);
                mSinVoicePlayer.play(text, true, 1000);
            }
        });

        Button playStop = (Button) this.findViewById(R.id.stop_play);
        playStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mSinVoicePlayer.stop();
            }
        });

        Button recognitionStart = (Button) this.findViewById(R.id.start_reg);
        recognitionStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mRecognition.start();
            }
        });

        Button recognitionStop = (Button) this.findViewById(R.id.stop_reg);
        recognitionStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mRecognition.stop();
            }
        });
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
    
    private void startHomePage() {
        // TODO 判断本地是否有登录数据和 Session，跳转到对应的页面    
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
    }

    @Override
    public void onPlayEnd() {
    }
}
