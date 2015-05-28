package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FindListInfo extends ResponseInfo {
    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data {
        @SerializedName("uid")
        public String uid;

        @SerializedName("name")
        public String name;

        @SerializedName("birth")
        public int age; // 年龄

        @SerializedName("degree")
        public int degree; // 匹配度

        @SerializedName("head")
        public String headUrl;

        @SerializedName("tag")
        public ArrayList<String> tags;
    }
}
