package cn.les.base.exception;

public class ValidateException extends RuntimeException {
    public ValidateException() {
        super();
    }

    public ValidateException(String msg) {
        super(msg);
    }
}
