package com.name.rmedal;

import android.app.Application;

import com.veni.rxtools.RxLogTool;
import com.veni.rxtools.RxTool;

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
