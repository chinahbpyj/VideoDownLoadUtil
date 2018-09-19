package com.pyj.videodownload.util;

public class StringUtil {
    public static boolean isEmpty(String value) {
        return isEmpty(value, null);
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value, String ignore) {
        if (value == null || value.trim().length() == 0) {
            return true;
        } else {
            if (ignore != null && value.equalsIgnoreCase(ignore)) {
                return true;
            }
        }

        return false;
    }
}

