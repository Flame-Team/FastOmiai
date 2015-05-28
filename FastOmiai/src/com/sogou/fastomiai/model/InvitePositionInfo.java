package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class InvitePositionInfo extends ResponseInfo {
    @SerializedName("data")
    public ArrayList<Position> data;

    public class Position {
        @SerializedName("id")
        public int id; // 地点 id

        @SerializedName("name")
        public String name; // 地点名称

        @SerializedName("lng")
        public float lng; // 经度

        @SerializedName("lat")
        public float lat; // 纬度
    }
}
