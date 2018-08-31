package com.name.rmedal.tools.zxing;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

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

        private CharSequence content;

        public Builder backColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder codeColor(int codeColor) {
            this.codeColor = codeColor;
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
            Bitmap bitmap = BarCode.createBarCode(content, codeWidth, codeHeight, backgroundColor, codeColor);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }
    }

    //-----------以下为生成二维码算法

    public static Bitmap createBarCode(CharSequence content, int BAR_WIDTH, int BAR_HEIGHT, int backgroundColor, int codeColor) {
        Bitmap bitmap = null;
         //条形码的编码类型
         BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
         final int backColor = backgroundColor;
         final int barCodeColor = codeColor;

         MultiFormatWriter writer = new MultiFormatWriter();
         BitMatrix result = null;
         try {
         result = writer.encode(content + "", barcodeFormat, BAR_WIDTH, BAR_HEIGHT, null);
         } catch (WriterException ignored) {
         }

         int width = result.getWidth();
         int height = result.getHeight();
         int[] pixels = new int[width * height];
         // All are 0, or black, by default
         for (int y = 0; y < height; y++) {
         int offset = y * width;
         for (int x = 0; x < width; x++) {
         pixels[offset + x] = result.get(x, y) ? barCodeColor : backColor;
         }
         }
         bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
         bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
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
        return createBarCode(contents, desiredWidth, desiredHeight, 0xFF000000, 0xFFFFFFFF);
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
