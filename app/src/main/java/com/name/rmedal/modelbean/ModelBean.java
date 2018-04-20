package com.name.rmedal.modelbean;

import java.io.Serializable;

/**
 * 作者：kkan on 2018/02/26
 * 当前类注释:
 */

public class ModelBean  implements Serializable {

    /**
     * obj : {}
     */

    private Object obj;

    public Object getObj() {
        return obj == null ? "" : obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
