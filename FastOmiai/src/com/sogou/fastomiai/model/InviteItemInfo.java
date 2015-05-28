package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

public class InviteItemInfo extends ResponseInfo {
    @SerializedName("data")
    public InviteItem data;

    public class InviteItem {
        @SerializedName("status")
        public StatusEnum status;

        @SerializedName("type")
        public TypeEnum type;

        @SerializedName("user")
        public User user;

        @SerializedName("position")
        public InvitePositionInfo.Position position = null;
    }

    public enum StatusEnum {
        @SerializedName("0")
        UNHANDLE, // 未处理

        @SerializedName("1")
        WAITFOR_DATE, // 待见面

        @SerializedName("2")
        REFUSED, // 拒绝

        @SerializedName("3")
        FINISHED, // 已完成

        @SerializedName("4")
        CANCEL // 已取消
    }

    public enum TypeEnum {
        @SerializedName("1")
        SEND, // 发出的

        @SerializedName("2")
        RECEIVE // 收到的
    }

    public class User {
        // TODO 待填充
    }
}
