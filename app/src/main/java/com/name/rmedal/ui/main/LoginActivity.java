package com.name.rmedal.ui.main;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.name.rmedal.R;
import com.name.rmedal.api.AppConstant;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.tools.AnimationTools;
import com.veni.tools.ACache;
import com.veni.tools.CaptchaTime;
import com.veni.tools.DataTools;
import com.veni.tools.KeyboardTools;
import com.veni.tools.LogTools;
import com.veni.tools.RegTools;
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.KeyboardLayout;
import com.veni.tools.view.ToastTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 * 登录
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_logo_iv)
    ImageView loginLogoIv;
    @BindView(R.id.login_mobile_et)
    EditText loginMobileEt;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.login_captcha_et)
    EditText loginCaptchaEt;
    @BindView(R.id.login_get_captcha)
    TextView loginGetCaptcha;
    @BindView(R.id.login_content_ll)
    LinearLayout loginContentLl;
    @BindView(R.id.login_scrollView)
    ScrollView loginScrollView;
    @BindView(R.id.login_service)
    LinearLayout loginService;
    @BindView(R.id.login_root_iv)
    KeyboardLayout loginRootRv;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(LoginActivity.class)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    private String mobile = "";
    private String password = "";
    private String captcha = "";
    private CaptchaTime timeCount;
//    private int lastdist;
//    private boolean viewIsZoom = false;

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarTools.immersive(this);
        StatusBarTools.setPaddingSmart(this, loginRootRv);
        StatusBarTools.darkMode(this, true);
        setSwipeBackLayout(0);

        initEvent();
        loginRootRv.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight, int bottom) {
//                int dist = loginContentLl.getBottom() - bottom;
                if (isActive) {
                    scrollToBottom();
                }
                if (keyboardHeight > 0) {
//                    if (dist > 0 && lastdist != dist) {
//                        viewIsZoom = true;
//                        ZoomIn(dist);
//                    }
                    if (loginService.getVisibility() != View.INVISIBLE) {
                        loginService.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (loginService.getVisibility() != View.VISIBLE) {
//                            && viewIsZoom) {
//                        viewIsZoom = false;
//                        ZoomOut();
                        loginService.setVisibility(View.VISIBLE);
                    }
                }
//                lastdist = dist;
            }
        });
        long codetime = ACache.get(context).getAsTime(AppConstant.LG_Code);
        if (codetime / 1000 > 2) {
            timeCount = new CaptchaTime(loginGetCaptcha, codetime / 1000);
            loginMobileEt.setText(ACache.get(context).getAsString(AppConstant.LG_Code));
        }
    }

    /**
     * 弹出软键盘时将SVContainer滑到底
     */
    private void scrollToBottom() {

        loginScrollView.postDelayed(new Runnable() {

            @Override
            public void run() {
                loginScrollView.smoothScrollTo(0, loginScrollView.getBottom() + StatusBarTools.getStatusBarHeight(context));
            }
        }, 100);

    }

    @OnClick({R.id.login_content_ll, R.id.login_btn, R.id.login_get_captcha, R.id.login_about_us, R.id.login_contact_customer_service})
    public void onViewClicked(View view) {
        KeyboardTools.hideSoftInput(context);
        if (antiShake.check(view.getId())) return;
        switch (view.getId()) {
            case R.id.login_content_ll:
                break;
            case R.id.login_about_us://关于我们
                break;
            case R.id.login_contact_customer_service://联系客服
                break;
            case R.id.login_btn://登录
                if (DataTools.isNullString(mobile)) {
                    ToastTool.error("请输入手机号");
                    return;
                }
                if (!RegTools.isMobileExact(mobile)) {
                    ToastTool.error("请输入正确手机号");
                    return;
                }
                if (DataTools.isNullString(captcha)) {
                    ToastTool.error("请输入验证码");
                    return;
                }
                if (DataTools.isNullString(password)) {
                    ToastTool.error("请输入密码");
                    return;
                }
                break;
            case R.id.login_get_captcha://获取验证码
                if (DataTools.isNullString(mobile)) {
                    ToastTool.error("请输入手机号");
                    return;
                }
                if (!RegTools.isMobileExact(mobile)) {
                    ToastTool.error("请输入正确手机号");
                    return;
                }
                timeCount = new CaptchaTime(loginGetCaptcha, 60);

                ACache.get(context).put(AppConstant.LG_Code, mobile, ACache.TIME_MINUTE);
                break;
        }
    }

    private void initEvent() {
        loginMobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mobile = s.toString();
            }
        });
        loginPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    ToastTool.error("请输入数字或字母");
                    s.delete(temp.length() - 1, temp.length());
                    loginPasswordEt.setSelection(s.length());
                    return;
                }
                password = s.toString();
            }
        });
        loginCaptchaEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                captcha = s.toString();
            }
        });
    }

    private void ZoomIn(int dist) {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(loginContentLl, "translationY", 0.0f, -dist);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
        mAnimatorTranslateY.start();
        AnimationTools.zoomIn(loginLogoIv, 0.6f, dist);
    }

    private void ZoomOut() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(loginContentLl, "translationY", loginContentLl.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
        mAnimatorTranslateY.start();
        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
        AnimationTools.zoomOut(loginLogoIv, 0.6f);
    }
}
