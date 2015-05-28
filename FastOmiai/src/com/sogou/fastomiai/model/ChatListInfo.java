package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ChatListInfo extends ResponseInfo {
    @SerializedName("data")
    public ArrayList<OneChat> data;

    public class OneChat {
        @SerializedName("uid")
        public String uid;

        @SerializedName("head")
        public String headUrl;

        @SerializedName("name")
        public String name;

        @SerializedName("msg")
        public String msg;

        @SerializedName("time")
        public long time;
    }
}
