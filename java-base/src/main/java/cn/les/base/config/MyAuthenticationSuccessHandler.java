package cn.les.base.config;

import cn.les.base.utils.RequestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: wyz
 * Date: 2019/2/22
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUrl = request.getContextPath() + "/"; //缺省的登陆成功页面
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        //如果登陆前正在跳转页面，登录后应当回到原页面继续操作
        if(savedRequest != null) {
            redirectUrl = savedRequest.getRedirectUrl();
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter out = response.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("url", redirectUrl);
        out.write(RequestResult.ok(map).toString());
        out.flush();
        out.close();
    }
}
