package cn.les.base.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestResult {
    private Integer code;
    private String msg;
    private Object data;
    private Boolean success;

    public static RequestResult ok() {
        return new RequestResult(HttpCode.OK, "", null, true);
    }

    public static RequestResult ok(Object data) {
        return new RequestResult(HttpCode.OK, "", data, true);
    }

    public static RequestResult error(Integer code, String msg) {
        return new RequestResult(code, msg, null, false);
    }

    public static RequestResult error(Integer code, String msg, Object data) {
        return new RequestResult(code, msg, data, false);
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
