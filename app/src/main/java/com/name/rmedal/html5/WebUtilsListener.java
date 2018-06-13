package com.name.rmedal.html5;

import android.webkit.WebView;

/**
 * 回调错误或者正确
 * Created by xiyn on 2017/6/16.
 */
public interface WebUtilsListener {
    void onReceivedTitle(WebView view, String title);
}
