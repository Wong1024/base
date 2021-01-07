package cn.les.base.config;

import cn.les.base.utils.HttpCode;
import cn.les.base.utils.RequestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: wyz
 * Date: 2019/2/22
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter out = response.getWriter();
        out.write(RequestResult.error(HttpCode.LOGIN_FAILED, e.getMessage()).toString());
        out.flush();
        out.close();
    }
}
