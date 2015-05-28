package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class NoticeListInfo extends ResponseInfo {
    @SerializedName("data")
    public ArrayList<NoticeInfo> data;
    
    public static class NoticeInfo {
        @SerializedName("notice_id")
        public int noticeID;

        @SerializedName("title")
        public String title;

        @SerializedName("content")
        public String content;

        @SerializedName("type")
        public MsgTypeEnum type;

        @SerializedName("status")
        public MsgStatusEnum status;

        @SerializedName("from_uid")
        public String fromUid;

        @SerializedName("invite_id")
        public String inviteId; // 若 type = 1，则此字段为约会 id
    }

    public enum MsgTypeEnum {
        @SerializedName("0")
        SYSTEM_MSG,

        @SerializedName("1")
        USER_MSG
    }

    public enum MsgStatusEnum {
        @SerializedName("0")
        UNREAD,

        @SerializedName("1")
        READED
    }
}
