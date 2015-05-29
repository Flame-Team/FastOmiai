package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

public class InviteItemInfo extends ResponseInfo {
    @SerializedName("data")
    public InviteItem data;

    public class InviteItem {
        @SerializedName("status")
        public InviteStatusEnum status;

        @SerializedName("type")
        public InviteTypeEnum type;

        @SerializedName("user")
        public InviteUser user;

        @SerializedName("position")
        public InvitePosition position = null;
    }

    public enum SexEnum {
        @SerializedName("m")
        SEX_MALE,
        
        @SerializedName("w")
        SEX_FEMALE
    }
}
