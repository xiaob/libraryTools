package com.name.rmedal.modelbean;

import android.support.annotation.DrawableRes;

import com.veni.rxtools.interfaces.OnNoFastClickListener;

import java.io.Serializable;

/**
 * 作者：kkan on 2018/02/26
 * 当前类注释:
 */

public class FunctionBean implements Serializable {
    private String functionName;
    @DrawableRes
    private int functionImage =0;
    private OnNoFastClickListener noFastClickListener =null;

    public FunctionBean(String functionName,@DrawableRes int functionImage,OnNoFastClickListener noFastClickListener) {
        this.functionName = functionName;
        this.functionImage = functionImage;
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
}
