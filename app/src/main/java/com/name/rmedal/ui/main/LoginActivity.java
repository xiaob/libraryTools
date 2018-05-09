package com.name.rmedal.ui.main;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.ToastTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_logo_iv)
    ImageView loginLogoIv;
    @BindView(R.id.login_mobile_et)
    EditText loginMobileEt;
    @BindView(R.id.login_clean_phone)
    ImageView loginCleanPhone;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.login_clean_password)
    ImageView loginCleanPassword;
    @BindView(R.id.login_show_pwd)
    ImageView loginShowPwd;
    @BindView(R.id.login_captcha_et)
    EditText loginCaptchaEt;
    @BindView(R.id.login_clean_captcha)
    ImageView loginCleanCaptcha;
    @BindView(R.id.login_get_captcha)
    TextView loginGetCaptcha;
    @BindView(R.id.login_content_ll)
    LinearLayout loginContentLl;
    @BindView(R.id.login_scrollView)
    ScrollView loginScrollView;
    @BindView(R.id.login_service)
    LinearLayout loginService;
    @BindView(R.id.login_root_iv)
    RelativeLayout loginRootRv;

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

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度

    private String mobile = "";
    private String password = "";
    private String captcha = "";
    private CaptchaTime timeCount;

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarTools.immersive(this);
        StatusBarTools.setPaddingSmart(this, loginRootRv);
        StatusBarTools.darkMode(this, true);
        setSwipeBackLayout(0);

        if (isFullScreen(this)) {
//            AndroidBug5497Workaround.assistActivity(this);
        }
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        initEvent();
        long codetime = ACache.get(context).getAsTime(AppConstant.LG_Code);
        if (codetime / 1000 > 2) {
            timeCount = new CaptchaTime(loginGetCaptcha, codetime / 1000);
            loginMobileEt.setText(ACache.get(context).getAsString(AppConstant.LG_Code));
        }
    }

    @OnClick({R.id.login_clean_phone, R.id.login_clean_password, R.id.login_show_pwd
            , R.id.login_btn, R.id.login_clean_captcha, R.id.login_get_captcha
            , R.id.login_about_us, R.id.login_contact_customer_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_clean_phone:
                loginMobileEt.setText("");
                break;
            case R.id.login_clean_password:
                loginPasswordEt.setText("");
                break;
            case R.id.login_clean_captcha:
                loginCaptchaEt.setText("");
                break;
            case R.id.login_about_us://关于我们
                break;
            case R.id.login_contact_customer_service://联系客服
                break;
            case R.id.login_btn://登录
                KeyboardTools.hideSoftInput(context);
                if (DataTools.isNullString(mobile)) {
                    ToastTool.normal("请输入手机号");
                    return;
                }
//                if (RegTools.isMobileExact(mobile)) {
//                    ToastTool.normal("请输入正确手机号");
//                      return;
//                }
                if (DataTools.isNullString(captcha)) {
                    ToastTool.normal("请输入验证码");
                    return;

                }
                break;
            case R.id.login_show_pwd:
                if (loginPasswordEt.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    loginPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    loginShowPwd.setImageResource(R.mipmap.pass_visuable);
                } else {
                    loginPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    loginShowPwd.setImageResource(R.mipmap.pass_gone);
                }
                String pwd = loginPasswordEt.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    loginPasswordEt.setSelection(pwd.length());
                break;
            case R.id.login_get_captcha://获取验证码
                if (DataTools.isNullString(mobile)) {
                    ToastTool.normal("请输入手机号");
                    return;
                }
//                if (RegTools.isMobileExact(mobile)) {
//                    ToastTool.normal("请输入正确手机号");
//                      return;
//                }
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
                if (!TextUtils.isEmpty(s) && loginCleanPhone.getVisibility() == View.GONE) {
                    loginCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    loginCleanPhone.setVisibility(View.GONE);
                }
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
                password = s.toString();
                if (!TextUtils.isEmpty(s) && loginCleanPassword.getVisibility() == View.GONE) {
                    loginCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    loginCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    ToastTool.error("请输入数字或字母");
                    s.delete(temp.length() - 1, temp.length());
                    loginPasswordEt.setSelection(s.length());
                }
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
                if (!TextUtils.isEmpty(s) && loginCleanCaptcha.getVisibility() == View.GONE) {
                    loginCleanCaptcha.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    loginCleanCaptcha.setVisibility(View.GONE);
                }
            }
        });
        /*
         * 禁止键盘弹起的时候可以滚动
         */
        loginScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        loginScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = loginContentLl.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(loginContentLl, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        AnimationTools.zoomIn(loginLogoIv, 0.6f, dist);
                    }
                    loginService.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((loginContentLl.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(loginContentLl, "translationY", loginContentLl.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        AnimationTools.zoomOut(loginLogoIv, 0.6f);
                    }
                    loginService.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

}
