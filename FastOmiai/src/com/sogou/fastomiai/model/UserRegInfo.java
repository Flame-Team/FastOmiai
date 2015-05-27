package com.sogou.fastomiai.model;

import com.google.gson.annotations.SerializedName;

public class UserRegInfo extends ResponseInfo {
    @SerializedName("data")
    public String token;
}
