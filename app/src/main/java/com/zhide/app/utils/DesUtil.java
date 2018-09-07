package com.zhide.app.utils;

import java.nio.charset.Charset;

/**
 * Create by Admin on 2018/9/7
 */
public class DesUtil {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String Keys ="i2@83l#.";//密钥

    /**
     * 加密
     * @param srcStr
     * @return
     */
    public static String encrypt(String srcStr) {
        byte[] src = srcStr.getBytes(CHARSET);
        byte[] buf = Des.encrypt(src, Keys);
        return Des.parseByte2HexStr(buf);
    }

    /**
     * 解密
     *
     * @param hexStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String hexStr) {
        byte[] src = Des.parseHexStr2Byte(hexStr);
        byte[] buf = new byte[0];
        try {
            buf = Des.decrypt(src, Keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(buf, CHARSET);
    }
}
