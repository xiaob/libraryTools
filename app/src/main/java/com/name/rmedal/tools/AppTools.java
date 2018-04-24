package com.name.rmedal.tools;


import android.content.Context;

import com.name.rmedal.modelbean.UserBean;
import com.veni.tools.RxDataTool;
import com.veni.tools.RxEncryptTool;
import com.veni.tools.RxJsonTools;
import com.veni.tools.RxLogTool;
import com.veni.tools.RxSPTool;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * 作者：kkan on 2018/02/26
 * 当前类注释:
 */

public class AppTools {

    private static final String TAG = AppTools.class.getSimpleName();
    /**
     * AES 密钥
     */
    public static final String SECRETKEY = "jingtum2017tudou";

    /**
     * SP  用户数据
     */
    public static final String USERDATA = "user_data";



    public static void saveUserBean(Context context, String userdata) {
        RxSPTool.put(context, USERDATA, userdata);
    }

    public static UserBean getUserBean(Context context) {
        String value = (String) RxSPTool.get(context, USERDATA, "");
        UserBean userBean = RxJsonTools.parseObject(AppTools.desAESCode(value), UserBean.class);
        return userBean == null ? new UserBean() : userBean;
    }


    public static String encAESCode(HashMap<String, Object> param) {
        String content = RxJsonTools.toJson(param);
        RxLogTool.d(TAG, "加密前数据-->" + content);
        if (content == null) {
            return "";
        }
        byte[] conb = content.getBytes();
        byte[] secreb = SECRETKEY.getBytes();
        String encryptResultStr = RxEncryptTool.encryptAES2HexString(conb, secreb);
        RxLogTool.d(TAG, "加密后-->" + encryptResultStr);
        return encryptResultStr;
    }

    public static String desAESCode(String content) {
        if (RxDataTool.isEmpty(content)) {
            RxLogTool.d(TAG, "解密字符为空");
            return "";
        }
        RxLogTool.d(TAG, "解密前json数据--->" + content);
        byte[] secreb = SECRETKEY.getBytes();
        byte[] decryptResult = RxEncryptTool.decryptHexStringAES(content, secreb);
        String decryptString = null;
        try {
            decryptString = new String(decryptResult, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RxLogTool.d(TAG, "解密后json数据--->" + decryptString);
        return decryptString;
    }
}
