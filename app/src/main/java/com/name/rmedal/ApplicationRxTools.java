package com.name.rmedal;

import android.app.Application;

import com.veni.tools.FutileTools;
import com.veni.tools.LogTools;

/**
 * Created by vonde on 2016/12/23.
 */

public class ApplicationRxTools extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FutileTools.init(this);
        LogTools.init(this,BuildConfig.LOG_DEBUG,false);
    }

}
