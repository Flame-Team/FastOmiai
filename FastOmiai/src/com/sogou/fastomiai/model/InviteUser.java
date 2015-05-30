package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.sogou.fastomiai.model.InviteItemInfo.SexEnum;


public class InviteUser {
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
    
    @SerializedName("tag")
    public ArrayList<String> tags;
}