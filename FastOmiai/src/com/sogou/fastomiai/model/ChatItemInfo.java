package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ChatItemInfo extends ResponseInfo {
    @SerializedName("data")
    public ChatItem data;

    public class ChatItem {
        @SerializedName("user")
        public ArrayList<InviteUser> users;

        @SerializedName("chatlist")
        public ArrayList<ChatMsg> chatlist;
    }

    public class ChatMsg {
        @SerializedName("from_uid")
        public String fromUid;

        @SerializedName("type")
        public MsgTypeEnum type;

        @SerializedName("msg")
        public String msg;

        @SerializedName("int")
        public long time;
    }

    public enum MsgTypeEnum {
        @SerializedName("0")
        SEND, // 自己发送的

        @SerializedName("1")
        RECEIVE // 对方发送的
    }
}
