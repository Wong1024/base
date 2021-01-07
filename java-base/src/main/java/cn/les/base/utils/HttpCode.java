package cn.les.base.utils;

public class HttpCode {
    public static int OK = 2000;                        //请求成功
    public static int BAD_PARAM = 4001;                 //参数未通过校验或缺少参数
    public static int UNAUTHORIZED = 4010;              //未登录
    public static int LOGIN_FAILED = 4011;              //登录失败，用户名或密码错误
    public static int VERIFICATION_CODE = 4012;         //登录验证码错误
    public static int FORBIDDEN = 4030;                 //已登录，但没有访问权限
    public static int NOT_FOUND = 4040;                 //访问的资源不存在
    public static int INTERNAL_SERVER_ERROR = 5000;     //服务器错误
}
