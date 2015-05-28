package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

public class UserGetInfo extends ResponseInfo {
    @SerializedName("data")
    public Data data;

    public class Data {
        @SerializedName("uid")
        public String uid;

        @SerializedName("name")
        public String name;

        @SerializedName("head")
        public String headUrl; // 头像 URL

        @SerializedName("rep")
        public int reputation; // 信誉

        @SerializedName("choice")
        public int choice; // 剩余机会数

        @SerializedName("new_notice")
        public int newNotice; // 未读通知数

        @SerializedName("count")
        public int datedCount; // 成功约会次数
    }
}
