package com.name.rmedal.ui.main.model;

import com.name.rmedal.api.HttpManager;
import com.name.rmedal.tools.AppTools;
import com.name.rmedal.tools.RxHelper;
import com.name.rmedal.ui.main.contract.MainContract;
import com.veni.tools.DeviceTools;
import com.veni.tools.FutileTool;

import java.util.HashMap;

import rx.Observable;

/**
 * 作者：Administrator on 2017/12/04 10:36
 * 当前类注释:
 */
public class MainModel implements MainContract.Model {

    @Override
    public Observable<String> checkVersion(String type) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("type", type);
        String data = AppTools.encAESCode(param);
        String deviceIdIMEI = DeviceTools.getDeviceIdIMEI(FutileTool.getContext());
        String versionName = DeviceTools.getAppVersionName(FutileTool.getContext());
        return HttpManager.getInstance().getOkHttpUrlService().getLastVersion(data, deviceIdIMEI, versionName)
                .compose(RxHelper.<String>handleResult());
    }
}
