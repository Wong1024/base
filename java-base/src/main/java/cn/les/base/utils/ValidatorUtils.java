package cn.les.base.utils;

import cn.les.base.exception.ValidateException;

import java.util.regex.Pattern;

public class ValidatorUtils {
    public static void notNull(Object o, String msg) {
        if (o == null) {
            throw new ValidateException(msg);
        }
    }
    public static void notBlank(String o, String msg) {
        if (o == null || o.trim().length() == 0) {
            throw new ValidateException(msg);
        }
    }
    public static void isInteger(String str, String msg) {
        Pattern pattern = Pattern.compile("^[\\d]*$");
        if (!pattern.matcher(str).matches()) {
            throw new ValidateException(msg);
        }
    }
    public static void check(Boolean b, String msg) {
        if (!b) {
            throw new ValidateException(msg);
        }
    }
}
