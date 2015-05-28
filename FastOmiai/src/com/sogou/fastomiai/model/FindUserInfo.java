package com.sogou.fastomiai.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FindUserInfo extends ResponseInfo {
    @SerializedName("data")
    public UserInfo data;

    public class UserInfo {
        @SerializedName("uid")
        public String uid;

        @SerializedName("name")
        public String name;

        @SerializedName("birth")
        public int age;

        @SerializedName("degree")
        public int degree;

        @SerializedName("auth_car")
        public AuthEnum authCar;

        @SerializedName("auth_house")
        public AuthEnum authHouse;

        @SerializedName("auth_bank")
        public AuthEnum authBank;

        @SerializedName("photo")
        public ArrayList<String> photos;

        @SerializedName("video")
        public ArrayList<String> videos;

        @SerializedName("tag")
        public ArrayList<String> tags;

        @SerializedName("score")
        public float score;
    }

    public enum AuthEnum {
        @SerializedName("0")
        UNAUTH,

        @SerializedName("1")
        AUTHED
    }
}
