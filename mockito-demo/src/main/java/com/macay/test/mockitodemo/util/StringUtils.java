package com.macay.test.mockitodemo.util;

/**
 * @ClassName: StringUtils
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/7 11:20 上午
 */
public class StringUtils {

    /**
     * 首字母变小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }
}
