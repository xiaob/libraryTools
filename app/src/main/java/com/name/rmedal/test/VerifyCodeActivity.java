package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.name.rmedal.R;
import com.name.rmedal.api.AppConstant;
import com.name.rmedal.base.BaseActivity;
import com.veni.tools.StatusBarUtil;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.TitleView;
import com.veni.tools.view.ToastTool;
import com.veni.tools.view.VerificationCodeView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 */

public class VerifyCodeActivity extends BaseActivity {

    @BindView(R.id.ticker_title_view)
    TitleView tickerTitleView;
    @BindView(R.id.verifycodeview)
    VerificationCodeView verifycodeview;
    @BindView(R.id.edit_input)
    EditText editInput;
    @BindView(R.id.net_pregressbar)
    ProgressBar netPregressbar;
    @BindView(R.id.net_verifycodeview)
    VerificationCodeView netVerifycodeview;
    @BindView(R.id.net_edit_input)
    EditText netEditInput;
    @BindView(R.id.verifycodeview2)
    VerificationCodeView verifycodeview2;

    /**
     * 启动入口
     */
    public static void startAction(Context context, View view) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(VerifyCodeActivity.class)
                .setView(view)
                .setActionString(AppConstant.TRANSITION_ANIMATION)
                .screenTransitAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verifycode;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        View view = tickerTitleView.getTvTitle();
        ViewCompat.setTransitionName(view, AppConstant.TRANSITION_ANIMATION);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, tickerTitleView);
        tickerTitleView.setLeftFinish(context);
        tickerTitleView.setTitle("随机验证码");
        setSwipeBackLayout(0);

        verifycodeview.refreshCode();
        verifycodeview2.refreshCode();
        getnetverifycode();
    }

    @OnClick({R.id.btn_submit, R.id.net_btn_submit,
            R.id.verifycodeview, R.id.net_verifycodeview_layut, R.id.verifycodeview2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String input = editInput.getText().toString().trim().toLowerCase();
                String code = verifycodeview.getvCode().toLowerCase();
                if (!TextUtils.isEmpty(input) && input.equals(code)) {
                    ToastTool.success("验证成功!Welcome");
                } else {
                    ToastTool.error("验证失败!请重试");
                }
                break;
            case R.id.verifycodeview:
                editInput.setText("");
                verifycodeview.refreshCode();
                break;
            case R.id.net_btn_submit:
                String netinput = netEditInput.getText().toString().trim().toLowerCase();
                String netcode = netVerifycodeview.getvCode().toLowerCase();
                if (!TextUtils.isEmpty(netinput) && netinput.equals(netcode)) {
                    ToastTool.success("验证成功!Welcome");
                } else {
                    ToastTool.error("验证失败!请重试");
                }
                break;
            case R.id.net_verifycodeview_layut:
                netEditInput.setText("");
                netPregressbar.setVisibility(View.VISIBLE);
                netVerifycodeview.setVisibility(View.GONE);
                getnetverifycode();
                break;
            case R.id.verifycodeview2:
                verifycodeview2.refreshCode();
                break;
        }
    }
    private void getnetverifycode(){

        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        String code=  getCharAndNumr();
                        netPregressbar.setVisibility(View.GONE);
                        netVerifycodeview.setVisibility(View.VISIBLE);
                        netVerifycodeview.setvCode(code);
                    }
                });
    }
    /**
     * java生成随机数字和字母组合
     * @return 随机验证码
     */
    public String getCharAndNumr() {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
