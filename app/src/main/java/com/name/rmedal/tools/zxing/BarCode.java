package com.name.rmedal.tools.zxing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by kkan on 2017/2/17.
 * 条形码
 * <p>
 * <p>
 * //条形码生成方式一  推荐此方法
 * BarCode.builder(str1).
 * backColor(getResources().getColor(R.color.transparent)).
 * codeColor(getResources().getColor(R.color.black)).
 * codeWidth(1000).
 * codeHeight(300).
 * into(mIvBarCode);
 * <p>
 * //条形码生成方式二  默认宽为1000 高为300 背景为白色 二维码为黑色
 * //mIvBarCode.setImageBitmap(BarCode.createBarCode(str1, 1000, 300));
 */

public class BarCode {

    /**
     * 获取条形码建造者
     *
     * @param text 样式字符串文本
     * @return {@link BarCode.Builder}
     */
    public static BarCode.Builder builder(@NonNull CharSequence text) {
        return new BarCode.Builder(text);
    }

    public static class Builder {

        private int backgroundColor = 0xffffffff;

        private int codeColor = 0xff000000;

        private int codeWidth = 1000;

        private int codeHeight = 300;

        private boolean isShowContent = false;

        private CharSequence content;

        public Builder backColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder codeColor(int codeColor) {
            this.codeColor = codeColor;
            return this;
        }

        public Builder isShowContent(boolean isShowContent) {
            this.isShowContent = isShowContent;
            return this;
        }

        public Builder codeWidth(int codeWidth) {
            this.codeWidth = codeWidth;
            return this;
        }

        public Builder codeHeight(int codeHeight) {
            this.codeHeight = codeHeight;
            return this;
        }

        public Builder(@NonNull CharSequence text) {
            this.content = text;
        }

        public Bitmap into(ImageView imageView) {
            Bitmap bitmap = BarCode.createBarCode(content, codeWidth, codeHeight, backgroundColor, codeColor,isShowContent);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }
    }

    //-----------以下为生成二维码算法

    public static Bitmap createBarCode(CharSequence content, int bar_width, int bar_height, int backgroundColor, int codeColor,boolean isShowContent) {
        Bitmap bitmap = null;
        //配置参数
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 容错级别 这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            // 图像数据转换，使用了矩阵转换 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bitMatrix = writer.encode(content.toString(), BarcodeFormat.CODE_128, bar_width, bar_height, hints);
            int[] pixels = new int[bar_width * bar_height];
//             下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < bar_height; y++) {
                for (int x = 0; x < bar_width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * bar_width + x] = codeColor;
                    } else {
                        pixels[y * bar_width + x] = backgroundColor;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(bar_width, bar_height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, bar_width, 0, 0, bar_width, bar_height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //----------------------------------------------------------------------------------------------生成条形码开始

    /**
     * 生成条形码
     *
     * @param contents      需要生成的内容
     * @param desiredWidth  生成条形码的宽带
     * @param desiredHeight 生成条形码的高度
     * @return
     */
    public static Bitmap createBarCode(String contents, int desiredWidth, int desiredHeight) {
        return createBarCode(contents, desiredWidth, desiredHeight, 0xFF000000, 0xFFFFFFFF,false);
    }

    /**
     * 生成条形码
     * desiredWidth  生成条形码的宽带
     * desiredHeight 生成条形码的高度
     *
     * @param contents 需要生成的内容
     * @return 条形码的Bitmap
     */
    public static Bitmap createBarCode(String contents) {
        return createBarCode(contents, 1000, 300);
    }

    public static void createBarCode(String content, int codeWidth, int codeHeight, ImageView iv_code) {
        iv_code.setImageBitmap(createBarCode(content, codeWidth, codeHeight));
    }

    public static void createBarCode(String content, ImageView iv_code) {
        iv_code.setImageBitmap(createBarCode(content));
    }

    //==============================================================================================生成条形码结束
}
