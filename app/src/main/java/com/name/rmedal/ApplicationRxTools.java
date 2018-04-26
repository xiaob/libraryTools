package com.name.rmedal;

import android.app.Application;

import com.veni.tools.LogTools;
import com.veni.tools.FutileTool;

/**
 * Created by vonde on 2016/12/23.
 */

public class ApplicationRxTools extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FutileTool.init(this);
        LogTools.init(this,BuildConfig.LOG_DEBUG,false);
    }

}
