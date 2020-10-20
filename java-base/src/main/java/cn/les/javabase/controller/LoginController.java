package cn.les.javabase.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     */
    @PostMapping("/logins")
    public void doLogin(String username, String password) {

    }

    @DeleteMapping("/logins")
    public void doLogout() {

    }
}
