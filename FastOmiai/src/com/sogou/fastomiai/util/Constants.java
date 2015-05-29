package com.sogou.fastomiai.util;

public class Constants {
    private Constants() {
    }

    // HTTP 接口及参数
    public static final String ROOT_URL = "http://123.57.132.203:8080/?method=";
    public static final String USER_REG_URL = ROOT_URL + "user.reg";
    public static final String USER_SUPP_URL = ROOT_URL + "user.supp";
    public static final String USER_LOGIN_URL = ROOT_URL + "user.login";
    public static final String USER_AUTH_URL = ROOT_URL + "user.auth";
    public static final String USER_GET_URL = ROOT_URL + "user.get";
    public static final String FIND_LIST_URL = ROOT_URL + "find.list";
    public static final String FIND_USER_URL = ROOT_URL + "find.user";
    public static final String INVITE_SEND_URL = ROOT_URL + "invite.send";
    public static final String NOTICE_LIST_URL = ROOT_URL + "notice.list";
    public static final String NOTICE_VIEW_URL = ROOT_URL + "notice.view";
    public static final String INVITE_ITEM_URL = ROOT_URL + "invite.item";
    public static final String INVITE_DEAL_URL = ROOT_URL + "invite.deal";
    public static final String INVITE_POSITION_URL = ROOT_URL + "invite.position";
    public static final String INVITE_FINISH_URL = ROOT_URL + "invite.finish";
    public static final String INVITE_COMMENT_URL = ROOT_URL + "invite.comment";
    public static final String USER_INVITE_URL = ROOT_URL + "user.invite";
    public static final String CHAT_ADD_URL = ROOT_URL + "chat.add";
    public static final String CHAT_LIST_URL = ROOT_URL + "chat.list";
    public static final String CHAT_ITEM_URL = ROOT_URL + "chat.item";
    
    public static final String TOKEN = "token";
    public static final String VERIFY = "verify";
    public static final String PHONE = "phone";
    public static final String START = "start";
    public static final String LIMIT = "limit";
    public static final String UID = "uid";
    public static final String ID = "id";
    public static final String ACT = "act";
    public static final String POSITION = "position";
    public static final String POS = "pos";
    public static final String SCORE = "score";
    public static final String COMMENT = "comment";
    public static final String TO_UID = "to_uid";
    public static final String MSG = "msg";
    public static final String NAME = "name";
    public static final String SEX = "sex";
    public static final String BIRTH = "birth";
    public static final String HEIGHT = "height";
    public static final String INCOME = "income";
    public static final String IS_CAR = "is_car";
    public static final String IS_HOUSE = "is_house";
    public static final String EDU = "edu";
    public static final String LEVEL = "level";
    public static final String MARRIAGE = "marriage";
    public static final String PHOTO = "photo";
    public static final String VIDEO = "video";
    
 // NOTIFICATION FIELDS

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    public static final String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";

    public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";

    public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

    public static final String NOTIFICATION_URI = "NOTIFICATION_URI";

    // INTENT ACTIONS

    public static final String ACTION_SHOW_NOTIFICATION = "org.androidpn.client.SHOW_NOTIFICATION";

    public static final String ACTION_NOTIFICATION_CLICKED = "org.androidpn.client.NOTIFICATION_CLICKED";

    public static final String ACTION_NOTIFICATION_CLEARED = "org.androidpn.client.NOTIFICATION_CLEARED";

}
