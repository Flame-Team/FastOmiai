package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class InvitePositionInfo extends ResponseInfo {
    @SerializedName("data")
    public ArrayList<InvitePosition> data;
}
