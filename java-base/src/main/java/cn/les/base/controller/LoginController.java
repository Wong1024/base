package cn.les.base.controller;

import cn.les.base.dto.LoginUser;
import cn.les.base.dto.MenuDTO;
import cn.les.base.dto.PermissionDTO;
import cn.les.base.dto.UserDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IMenuService;
import cn.les.base.service.IPermissionService;
import cn.les.base.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private IUserService userService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private IMenuService menuService;

    /**
     * 获得当前登录用户的信息
     *
     * @return 用户信息
     * @throws ResourceNotFoundException 找不到登录用户信息
     */
    @GetMapping("/user")
    public UserDTO fetchLoginUser() throws ResourceNotFoundException {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userService.fetchUserByUsername(loginUser.getUsername());
    }

    /**
     * 获得当前登录用户可访问的接口权限列表
     *
     * @return 权限列表
     */
    @GetMapping("/permissions")
    List<PermissionDTO> fetchLoginPermissions() throws ResourceNotFoundException {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return permissionService.fetchLoginPermissionsByUserId(loginUser.getId());
    }

    /**
     * 获得当前用户可访问的菜单
     *
     * @return 菜单列表
     */
    @GetMapping("/menuTree")
    List<MenuDTO> fetchLoginMenuTree() throws ResourceNotFoundException {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return menuService.fetchMenuTreeByUserId(loginUser.getId());
    }
}
