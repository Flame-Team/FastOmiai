package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;
import com.sogou.fastomiai.model.InviteItemInfo.SexEnum;

/**
 *
 * @author mazhuang
 * 所有请求返回数据结构的基类
 * errno 表示返回码，必有字段，0 表示成功
 * errmsg 表示错误消息，出错时返回
 * data 是成功时返回的对象，与 errmsg 不共存
 * 子类里只用定义好各自的 data 就好了
 */

public class ResponseInfo {
    @SerializedName("errno")
    public int errno;
    
    @SerializedName("errmsg")
    public String errmsg = "";

    public boolean isSuccess() {
        return (errno == 0);
    }
}

// 在多个结构体中共用的枚举和子类定义
enum InviteStatusEnum {
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

enum InviteTypeEnum {
    @SerializedName("1")
    SEND, // 发出的

    @SerializedName("2")
    RECEIVE // 收到的
}

class InviteUser {
    @SerializedName("uid")
    public String uid;

    @SerializedName("name")
    public String name;

    @SerializedName("sex")
    public SexEnum sex;

    @SerializedName("birth")
    public int age;

    @SerializedName("degree")
    public int degree;

    @SerializedName("rep")
    public int reputation;

    @SerializedName("score")
    public float score;

    @SerializedName("head")
    public String headUrl;
}

class InvitePosition {
    @SerializedName("id")
    public int id = 0; // 地点 id

    @SerializedName("name")
    public String name; // 地点名称

    @SerializedName("lng")
    public float lng; // 经度

    @SerializedName("lat")
    public float lat; // 纬度
}
