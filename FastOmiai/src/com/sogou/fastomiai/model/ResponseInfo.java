package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

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
