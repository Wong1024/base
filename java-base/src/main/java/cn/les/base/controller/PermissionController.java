package cn.les.base.controller;

import cn.les.base.dto.LoginUser;
import cn.les.base.dto.PermissionDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IPermissionService;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PermissionController {
    @Resource
    private IPermissionService permissionService;

    @GetMapping("/permissions")
    List<PermissionDTO> fetchPermissions() {
        return permissionService.fetchPermissions();
    }

    @GetMapping("/permissions/{id}")
    PermissionDTO fetchPermissionById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return permissionService.fetchPermissionById(id);
    }

    @PostMapping("/permissions")
    void addPermission(@RequestBody PermissionDTO permission) {
        ValidatorUtils.notBlank(permission.getPermissionName(), "权限名不能为空");
        ValidatorUtils.notNull(permission.getType(), "权限类型不能为空");
        if (permission.getType() == 1) {
            ValidatorUtils.notBlank(permission.getPath(), "路径不能为空");
            ValidatorUtils.notBlank(permission.getMethod(), "请求类型不能为空");
        }
        permissionService.addPermission(permission);
    }

    @PutMapping("/permissions/{id}")
    void updatePermission(@PathVariable("id") Long id, @RequestBody PermissionDTO permission)
            throws ResourceNotFoundException {
        ValidatorUtils.notBlank(permission.getPermissionName(), "权限名不能为空");
        ValidatorUtils.notNull(permission.getType(), "权限类型不能为空");
        if (permission.getType() == 1) {
            ValidatorUtils.notBlank(permission.getPath(), "路径不能为空");
            ValidatorUtils.notBlank(permission.getMethod(), "请求类型不能为空");
        }
        permission.setId(id);
        permissionService.updatePermission(permission);
    }

    @DeleteMapping("/permissions/{id}")
    void removePermission(@PathVariable("id") Long id) throws ResourceNotFoundException {
        permissionService.removePermission(id);
    }

}
