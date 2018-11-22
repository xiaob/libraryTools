package com.name.rmedal.modelbean;

import android.support.annotation.DrawableRes;

import com.veni.tools.interfaces.OnNoFastClickListener;
import com.veni.tools.view.progressing.Style;

import java.io.Serializable;

/**
 * 作者：kkan on 2018/02/26
 * 当前类注释:
 * 首页功能模块实体类(不是必须)
 */

public class FunctionBean implements Serializable {
    private String functionName;

    private int functionImage = 0;

    private OnNoFastClickListener noFastClickListener = null;

    private Style style = null;

    public FunctionBean(String functionName, @DrawableRes int functionImage, OnNoFastClickListener noFastClickListener) {
        this.functionName = functionName;
        this.functionImage = functionImage;
        this.noFastClickListener = noFastClickListener;
    }

    public FunctionBean(String functionName, Style style, OnNoFastClickListener noFastClickListener) {
        this.functionName = functionName;
        this.style = style;
        this.noFastClickListener = noFastClickListener;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getFunctionImage() {
        return functionImage;
    }

    public void setFunctionImage(@DrawableRes int functionImage) {
        this.functionImage = functionImage;
    }

    public OnNoFastClickListener getNoFastClickListener() {
        return noFastClickListener;
    }

    public void setNoFastClickListener(OnNoFastClickListener noFastClickListener) {
        this.noFastClickListener = noFastClickListener;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
