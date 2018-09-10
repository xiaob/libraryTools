package com.name.rmedal.api;

/**
 * 作者：kkan on 2017/11/30
 * 当前类注释:
 *   APP常量
 */
public class AppConstant {
    public static final String  pageSize = "20";
    public static final String  PatternlockKey = "Patternlock";//手势密码
    public static final String  PatternlockOK = "Patternlock_ok";//是否验证过手势密码
    public static final String  FIRST_TIME = "first_time";//是否第一次登陆
    public static final String LG_Code="login_Code";
    public static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    // 消息通知 0:允许 1：不允许
    public static final String MESSAGE_STATE = "message_state";

    public static final int GET_IMAGE_FROM_PHONE = 5000;//相册二维码识别
    public static final int REQUEST_QRCODE = 5001;//二维码扫描


    /*浮动按钮 RxBus 事件*/
    public static final String MENU_SHOW_HIDE="MENU_SHOW_HIDE";


    public static final String TRANSITION_ANIMATION = "transition_animation";

}
