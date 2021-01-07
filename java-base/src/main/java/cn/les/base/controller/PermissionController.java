package cn.les.base.controller;

import cn.les.base.dto.LoginUser;
import cn.les.base.dto.PermissionDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IPermissionService;
import cn.les.base.utils.RequestResult;
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
    public RequestResult fetchPermissions() {
        return RequestResult.ok(permissionService.fetchPermissions());
    }

    @GetMapping("/permissions/{id}")
    public RequestResult fetchPermissionById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return RequestResult.ok(permissionService.fetchPermissionById(id));
    }

    @PostMapping("/permissions")
    public RequestResult addPermission(@RequestBody PermissionDTO permission) {
        ValidatorUtils.getInstance()
                .notBlank(permission.getPermissionName(), "权限名不能为空")
                .notNull(permission.getType(), "权限类型不能为空");
        if (permission.getType() == 1) {
            ValidatorUtils.getInstance()
                    .notBlank(permission.getPath(), "路径不能为空")
                    .notBlank(permission.getMethod(), "请求类型不能为空");
        }
        return RequestResult.ok(permissionService.addPermission(permission));
    }

    @PutMapping("/permissions/{id}")
    public RequestResult updatePermission(@PathVariable("id") Long id, @RequestBody PermissionDTO permission)
            throws ResourceNotFoundException {
        ValidatorUtils.getInstance()
                .notBlank(permission.getPermissionName(), "权限名不能为空")
                .notNull(permission.getType(), "权限类型不能为空");
        if (permission.getType() == 1) {
            ValidatorUtils.getInstance()
                    .notBlank(permission.getPath(), "路径不能为空")
                    .notBlank(permission.getMethod(), "请求类型不能为空");
        }
        permission.setId(id);
        return RequestResult.ok(permissionService.updatePermission(permission));
    }

    @DeleteMapping("/permissions/{id}")
    public RequestResult removePermission(@PathVariable("id") Long id) throws ResourceNotFoundException {
        permissionService.removePermission(id);
        return RequestResult.ok();
    }

}
