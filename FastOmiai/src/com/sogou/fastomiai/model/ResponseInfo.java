package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

public class ResponseInfo {
    @SerializedName("errno")
    public int errno;
    
    public boolean isSuccess() {
        return (errno == 0);
    }
}
