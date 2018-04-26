package com.name.rmedal.modelbean;

/**
 * 作者：kkan on 2018/04/26
 * 当前类注释:
 */

public class DeviceBean {

    private String userName;
    private String nickName;

    public DeviceBean(String userName, String nickName) {
        this.userName = userName;
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
