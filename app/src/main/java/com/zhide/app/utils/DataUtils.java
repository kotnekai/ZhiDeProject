package com.zhide.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Create by Admin on 2018/10/9
 */
public class DataUtils {
    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式:验证汉字(1-9个汉字)  {1,9} 自定义区间
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$";

    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    /**
     * 正则表达式:验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式:验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIPAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress);
    }

    /**
     * 二代身份证号码有效性校验
     *
     * @param idNo
     * @return
     */
    public static boolean isValidIdNo(String idNo) {
        return isIdNoPattern(idNo) && isValidProvinceId(idNo.substring(0, 2))
                && isValidDate(idNo.substring(6, 14)) && checkIdNoLastNum(idNo);
    }

    /**
     * 二代身份证正则表达式
     *
     * @param idNo
     * @return
     */
    private static boolean isIdNoPattern(String idNo) {
        return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$", idNo);
    }

    //省(直辖市)码表
    private static String provinceCode[] = {"11", "12", "13", "14", "15", "21", "22",
            "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
            "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
            "64", "65", "71", "81", "82", "91"};

    /**
     * 检查身份证的省份信息是否正确
     *
     * @param provinceId
     * @return
     */
    public static boolean isValidProvinceId(String provinceId) {
        for (String id : provinceCode) {
            if (id.equals(provinceId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断日期是否有效
     *
     * @param inDate
     * @return
     */
    public static boolean isValidDate(String inDate) {
        if (inDate == null) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }
        dateFormat.setLenient(false);//执行严格的日期匹配
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    //身份证前17位每位加权因子
    private static int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    //身份证第18位校检码
    private static String[] refNumber = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * 计算身份证的第十八位校验码
     *
     * @param cardIdArray
     * @return
     */
    public static String sumPower(int[] cardIdArray) {
        int result = 0;
        for (int i = 0; i < power.length; i++) {
            result += power[i] * cardIdArray[i];
        }
        return refNumber[(result % 11)];
    }

    /**
     * 校验身份证第18位是否正确(只适合18位身份证)
     *
     * @param idNo
     * @return
     */
    public static boolean checkIdNoLastNum(String idNo) {
        if (idNo.length() != 18) {
            return false;
        }
        char[] tmp = idNo.toCharArray();
        int[] cardidArray = new int[tmp.length - 1];
        int i = 0;
        for (i = 0; i < tmp.length - 1; i++) {
            cardidArray[i] = Integer.parseInt(tmp[i] + "");
        }
        String checkCode = sumPower(cardidArray);
        String lastNum = tmp[tmp.length - 1] + "";
        if (lastNum.equals("x")) {
            lastNum = lastNum.toUpperCase();
        }
        if (!checkCode.equals(lastNum)) {
            return false;
        }
        return true;
    }
}
