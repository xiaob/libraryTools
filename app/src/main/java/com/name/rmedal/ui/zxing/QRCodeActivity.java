package com.name.rmedal.ui.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.name.rmedal.R;
import com.name.rmedal.api.AppConstant;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.tools.zxing.BarCode;
import com.name.rmedal.tools.zxing.QRCode;
import com.name.rmedal.tools.zxing.QrBarTool;
import com.veni.tools.ACache;
import com.veni.tools.DataTools;
import com.veni.tools.ImageTools;
import com.veni.tools.LogTools;
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.TitleView;
import com.veni.tools.view.ToastTool;
import com.veni.tools.view.ticker.TickerUtils;
import com.veni.tools.view.ticker.TickerView;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 * 二维码
 */
public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.qrcode_title_view)
    TitleView qrcodeTitleView;
    @BindView(R.id.qrcode_create_count)
    TickerView qrcodeCreateCount;//生成二维码次数
    @BindView(R.id.qrcode_scaner_count)
    TickerView qrcodeScanerCount;//扫码次数
    @BindView(R.id.qrcode_create_codeiv)
    ImageView qrcodeCreateCodeiv;
    @BindView(R.id.qrcode_create_codetip)
    TextView qrcodeCreateCodetip;
    @BindView(R.id.qrcode_create_codeiv_rl)
    LinearLayout qrcodeCreateCodeivRl;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(QRCodeActivity.class)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initPresenter() {

    }

    private int create_count;
    private int scaner_count;
    private int bitmap_height=900;
    private int bitmap_width=1000;
    private int bitmap_math=900;

    private Bitmap create_bitmap;

    @Override
    public void initView(Bundle savedInstanceState) {
        //设置沉侵状态栏
        StatusBarTools.immersive(this);
        //增加状态栏的高度
        StatusBarTools.setPaddingSmart(this, qrcodeTitleView);
        //设置返回点击事件
        qrcodeTitleView.setLeftFinish(context);
        //设置显示标题
        qrcodeTitleView.setTitle("二维码");
        //设置侧滑退出
        setSwipeBackLayout(0);
        Object cc = ACache.get(context).getAsObject(AppConstant.CreateCount);
        Object sc = ACache.get(context).getAsObject(AppConstant.ScanerCount);
        create_count = cc == null ? 0 : (int) cc;
        scaner_count = sc == null ? 0 : (int) sc;
        //设置需要滚动的字符
        qrcodeCreateCount.setCharacterList(TickerUtils.getDefaultNumberList());
        qrcodeScanerCount.setCharacterList(TickerUtils.getDefaultNumberList());
        //初始化数据
        upTickerViews();
        //设置监听
        initListener();
    }

    @OnClick({R.id.qrcode_scaner_ll, R.id.qrcode_create_qr, R.id.qrcode_create_bar})
    public void onViewClicked(View view) {
        if (antiShake.check(view.getId())) return;
        switch (view.getId()) {
            case R.id.qrcode_scaner_ll://扫码
                Intent intent = new Intent();
                intent.setClass(context, ScanerCodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, AppConstant.REQUEST_QRCODE);
                break;
            case R.id.qrcode_create_qr://生成二维码
                qrcodeCreateCodetip.setText("长按图片识别二维码");
                create_bitmap = QRCode.builder(getCharAndNumr()).
                        backColor(getResources().getColor(R.color.white)).
                        codeColor(getResources().getColor(R.color.black)).
                        codeSide(bitmap_math).
                        into(qrcodeCreateCodeiv);
                upCreateData();
                break;
            case R.id.qrcode_create_bar://生成条形码
                qrcodeCreateCodetip.setText("长按图片识别条形码");
                create_bitmap = BarCode.builder( getCharAndNumr()).
                        backColor(getResources().getColor(R.color.white)).
                        codeColor(getResources().getColor(R.color.black)).
                        codeWidth(bitmap_width).
                        codeHeight(bitmap_height/3).
                        isShowContent(true).
                        into(qrcodeCreateCodeiv);
                upCreateData();
                break;
        }
    }
    /**
     * java生成随机数字和字母组合
     */
    public String getCharAndNumr() {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 18; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (choice + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }
    private void initListener() {

        //设置长按识别二维码条形码
        qrcodeCreateCodeiv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (create_bitmap == null) {
                    return false;
                }
                // 开始对图像资源解码
                Result rawResult = QrBarTool.decodeFromPhoto(create_bitmap);
                if (rawResult != null) {
                    initDialogResult(rawResult);
                } else {
                    ToastTool.success("图片识别失败!");
                }
                return true;
            }
        });
        //获取二维码显示View的宽高
        qrcodeCreateCodeivRl.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){
                    @Override
                    public void onGlobalLayout(){
                        //只需要获取一次高度，获取后移除监听器
                        qrcodeCreateCodeivRl.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        //这里高度应该定义为成员变量，定义为局部为展示代码方便
                        bitmap_height = qrcodeCreateCodeivRl.getHeight()- ImageTools.dpToPx(context,40);
                        bitmap_width= qrcodeCreateCodeivRl.getWidth()- ImageTools.dpToPx(context,40);
                        if(bitmap_width>bitmap_height){
                            bitmap_math=bitmap_height;
                        }else {
                            bitmap_math=bitmap_width;
                        }
                    }
                });
    }

    private void upTickerViews() {
        qrcodeCreateCount.setText("" + create_count);
        qrcodeScanerCount.setText("" + scaner_count);
    }

    private void initDialogResult(Result result) {
        BarcodeFormat type = result.getBarcodeFormat();
        String realContent = result.getText();
        if (BarcodeFormat.QR_CODE.equals(type)) {//二维码扫描结果
            LogTools.v("二维码", realContent);
        } else if (BarcodeFormat.EAN_13.equals(type)) {//条形码扫描结果
            LogTools.v("条形码", realContent);
        } else {//扫描结果
            LogTools.v("扫描结果", realContent);
        }
        if (realContent.equals("")) {
            ToastTool.success("扫描失败!");
        } else {
            upScanerData(realContent);
        }
    }

    private void upCreateData() {
        create_count++;
        ACache.get(context).put(AppConstant.CreateCount, create_count);
        upTickerViews();
    }

    private void upScanerData(String zxqrcode) {
        scaner_count++;
        ACache.get(context).put(AppConstant.ScanerCount, scaner_count);
        creatDialogBuilder()
                .setDialog_title("扫描结果")
                .setDialog_message(zxqrcode)
                .setDialog_Left("确定")
                .setDialog_Right("取消")
                .setCancelable(true)
                .builder().show();
        upTickerViews();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) { // Successfully.
            if (requestCode == AppConstant.REQUEST_QRCODE) {//扫码回调
                Bundle bundle = data.getExtras();
                String zxqrcode = null;
                if (bundle != null) {
                    zxqrcode = bundle.getString("result");
                }
                LogTools.e(TAG, "" + zxqrcode);
                if (!DataTools.isNullString(zxqrcode)) {
                    upScanerData(zxqrcode);
                } else {
                    ToastTool.success("扫描失败!");
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) { // User canceled.
            LogTools.e(TAG, " User canceled.");
        }
    }
}
