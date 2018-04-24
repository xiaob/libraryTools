package com.name.rmedal.bigimage;

import java.io.Serializable;

/**
 * 作者：kkan on 2018/04/24
 * 当前类注释:
 */

public class BigImageBean implements Serializable {
    private String image_url;
    private String image_view_title;
    private String image_describe;

    public BigImageBean(String image_url, String image_describe, String image_view_title) {
        this.image_url = image_url;
        this.image_describe = image_describe;
        this.image_view_title = image_view_title;
    }

    public BigImageBean(String image_url, String image_describe) {
        this.image_url = image_url;
        this.image_describe = image_describe;
    }

    public String getImage_url() {
        return image_url == null ? "" : image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_describe() {
        return image_describe == null ? "" : image_describe;
    }

    public void setImage_describe(String image_describe) {
        this.image_describe = image_describe;
    }

    public String getImage_view_title() {
        return image_view_title == null ? "" : image_view_title;
    }

    public void setImage_view_title(String image_view_title) {
        this.image_view_title = image_view_title;
    }
}
