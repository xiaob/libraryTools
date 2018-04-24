package com.veni.tools.interfaces;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 作者：xiyn on 2017/12/19
 * 当前类注释:
 * 验证码倒计时
 */

public class TimeCount extends CountDownTimer {
    private TextView codeTv;
    private boolean countisfinish = true;

    public TimeCount(TextView codeTv, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.codeTv = codeTv;
    }

    public boolean isfinish() {
        return countisfinish;
    }

    @Override
    public void onFinish() {
        codeTv.setEnabled(true);
        codeTv.setText("重新获取");
        countisfinish = true;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        codeTv.setEnabled(false);
        countisfinish = false;
        codeTv.setText(millisUntilFinished / 1000 + "s" + "后重发");
    }
}
