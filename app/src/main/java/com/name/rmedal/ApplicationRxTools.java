package com.name.rmedal;

import android.app.Application;

import com.veni.tools.RxLogTool;
import com.veni.tools.RxTool;

/**
 * Created by vonde on 2016/12/23.
 */

public class ApplicationRxTools extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        RxLogTool.init(this,BuildConfig.LOG_DEBUG,false);
    }

}
