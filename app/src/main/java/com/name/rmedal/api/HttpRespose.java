package com.name.rmedal.api;

import java.io.Serializable;

/**
 * 作者：kkan on 2017/01/30
 * 当前类注释:
 *      封装服务器返回数据
 */
public class HttpRespose<T> implements Serializable {
    public String code;
    public String msg;

    public T data;

    public boolean success() {
        return "1001".equals(code);
    }

    @Override
    public String toString() {
        return "HttpRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
