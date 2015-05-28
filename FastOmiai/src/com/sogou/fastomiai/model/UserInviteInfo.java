package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class UserInviteInfo extends ResponseInfo {
    @SerializedName("data")
    public ArrayList<InviteInfo> data;

    public class InviteInfo {
        @SerializedName("id")
        public int id;

        @SerializedName("status")
        public InviteStatusEnum status;

        @SerializedName("type")
        public InviteTypeEnum type;

        @SerializedName("user")
        public InviteUser user;

        @SerializedName("position")
        public InvitePosition position;
    }
}
