package cn.les.base.utils;

import cn.les.base.exception.ValidateException;

import java.util.regex.Pattern;

public class ValidatorUtils {
    private static ValidatorUtils instance;

    public static ValidatorUtils getInstance() {
        if (instance == null) {
            instance = new ValidatorUtils();
        }
        return instance;
    }

    private ValidatorUtils () {
    }

    /**
     * 检查对象非空
     *
     * @param o 校验对象
     * @param msg 报错信息
     * @return 链式调用
     */
    public ValidatorUtils notNull(Object o, String msg) {
        if (o == null) {
            throw new ValidateException(msg);
        }
        return this;
    }

    /**
     * 检查字符串非空
     *
     * @param o 校验对象
     * @param msg 报错信息
     * @return 链式调用
     */
    public ValidatorUtils notBlank(String o, String msg) {
        if (o == null || o.trim().length() == 0) {
            throw new ValidateException(msg);
        }
        return this;
    }

    /**
     * 检查字符串是否整数
     *
     * @param str 校验对象
     * @param msg 报错信息
     * @return 链式调用
     */
    public ValidatorUtils isInteger(String str, String msg) {
        Pattern pattern = Pattern.compile("^[\\d]*$");
        if (!pattern.matcher(str).matches()) {
            throw new ValidateException(msg);
        }
        return this;
    }

    /**
     * 自定义校验
     *
     * @param b 校验表达式
     * @param msg 报错信息
     * @return 链式调用
     */
    public ValidatorUtils check(Boolean b, String msg) {
        if (!b) {
            throw new ValidateException(msg);
        }
        return this;
    }
}
