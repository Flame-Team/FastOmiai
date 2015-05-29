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
        
        @SerializedName("pos")
        public String pos;
    }

    public enum SexEnum {
        @SerializedName("m")
        SEX_MALE,
        
        @SerializedName("w")
        SEX_FEMALE;
        
        public String toString() {
            String[] values = {"m", "w"};
            return values[ordinal()];
        }
    }
}
