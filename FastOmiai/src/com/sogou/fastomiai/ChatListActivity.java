package com.sogou.fastomiai;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.sogou.fastomiai.model.ChatListInfo.OneChat;
import com.sogou.fastomiai.util.NetworkRequest;

public class ChatListActivity extends Activity {
    private ListView mList;
    private ImageButton mBtnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        
        mList = (ListView)findViewById(R.id.chat_list);
        
        // TODO 此处 Adapter 的数据应该是从网上拿
        ArrayList<OneChat> chats = new ArrayList<OneChat>();
        OneChat chat1 = new OneChat();
        OneChat chat2 = new OneChat();
        chats.add(chat1);
        chats.add(chat2);
        ChatListAdapter listAdapter = new ChatListAdapter(chats);
        
        mList.setAdapter(listAdapter);
        
        mBtnBack = (ImageButton) findViewById(R.id.btn_chat_list_back);
        mBtnBack.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
    
    private class ChatListAdapter extends ArrayAdapter<OneChat> {
        public ChatListAdapter(ArrayList<OneChat> infos) {
            super(ChatListActivity.this, 0, infos);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ChatListActivity.this.getLayoutInflater()
                        .inflate(R.layout.list_item_chat, null);
            }
            
            final OneChat chat = getItem(position);
            
            NetworkImageView imageHead = (NetworkImageView)convertView.findViewById(R.id.image_chat_head);
            if (chat.headUrl != null) {
                imageHead.setImageUrl(chat.headUrl, 
                        NetworkRequest.getInstance(getApplicationContext()).getImageLoader());
            }
            TextView textTip = (TextView)convertView.findViewById(R.id.text_chat_tip);
            if (chat.msg != null) {
                textTip.setText(chat.msg);
            }
            
            LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.layout_chat_item);
            layout.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                	Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
    			    startActivity(intent);  
                }
            });
            
            return convertView;
        }
    }
}
