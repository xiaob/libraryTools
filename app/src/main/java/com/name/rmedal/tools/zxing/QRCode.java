package com.name.rmedal.tools.zxing;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by kkan on 2017/2/17.
 * 二维码
 * <p>
 * //二维码生成方式一  推荐此方法
 * QRCode.builder(str).
 * backColor(getResources().getColor(R.color.white)).
 * codeColor(getResources().getColor(R.color.black)).
 * codeSide(600).
 * into(mIvQrCode);
 * <p>
 * //二维码生成方式二 默认宽和高都为800 背景为白色 二维码为黑色
 * // QRCode.createQRCode(str,mIvQrCode);
 */

public class QRCode {

    /**
     * 获取二维码建造者
     *
     * @param text 样式字符串文本
     * @return {@link QRCode.Builder}
     */
    public static QRCode.Builder builder(@NonNull CharSequence text) {
        return new QRCode.Builder(text);
    }

    public static class Builder {

        private int backgroundColor = 0xffffffff;

        private int codeColor = 0xff000000;

        private int codeSide = 800;

        private CharSequence content;

        public Builder backColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder codeColor(int codeColor) {
            this.codeColor = codeColor;
            return this;
        }

        public Builder codeSide(int codeSide) {
            this.codeSide = codeSide;
            return this;
        }

        public Builder(@NonNull CharSequence text) {
            this.content = text;
        }

        public Bitmap into(ImageView imageView) {
            Bitmap bitmap = QRCode.creatQRCode(content, codeSide, codeSide, backgroundColor, codeColor);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }
    }

    //----------------------------------------------------------------------------------------------以下为生成二维码算法

    public static Bitmap creatQRCode(CharSequence content, int qr_width, int qr_height, int backgroundColor, int codeColor) {
        Bitmap bitmap = null;
        try {
            // 判断URL合法性
            if (content == null || content.length() < 1) {
                return null;
            }

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content + "", BarcodeFormat.QR_CODE, qr_width, qr_height, hints);
            int[] pixels = new int[qr_width * qr_height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < qr_height; y++) {
                for (int x = 0; x < qr_width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * qr_width + x] = codeColor;
                    } else {
                        pixels[y * qr_width + x] = backgroundColor;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(qr_width, qr_height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, qr_width, 0, 0, qr_width, qr_height);
        } catch (WriterException ignored) {
        }
        return bitmap;
    }

    public static Bitmap creatQRCode(CharSequence content, int qr_width, int qr_height) {
        return creatQRCode(content, qr_width, qr_height, 0xffffffff, 0xff000000);
    }

    public static Bitmap creatQRCode(CharSequence content) {
        return creatQRCode(content, 800, 800);
    }

    //==============================================================================================二维码算法结束


    /**
     * @param content   需要转换的字符串
     * @param qr_width  二维码的宽度
     * @param qr_height 二维码的高度
     * @param iv_code   图片空间
     */
    public static void createQRCode(String content, int qr_width, int qr_height, ImageView iv_code) {
        iv_code.setImageBitmap(creatQRCode(content, qr_width, qr_height));
    }

    /**
     * qr_width  二维码的宽度
     * qr_height 二维码的高度
     *
     * @param content 需要转换的字符串
     * @param iv_code 图片空间
     */
    public static void createQRCode(String content, ImageView iv_code) {
        iv_code.setImageBitmap(creatQRCode(content));
    }
}
