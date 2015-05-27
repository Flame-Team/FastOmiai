package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

public class UserLoginInfo extends ResponseInfo {
    @SerializedName("data")
    public String token;
}
