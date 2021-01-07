package cn.les.base.service;

import cn.les.base.dto.PermissionDTO;
import cn.les.base.exception.ResourceNotFoundException;

import java.util.List;

public interface IPermissionService {
    List<PermissionDTO> fetchLoginPermissionsByUserId(Long id) throws ResourceNotFoundException;

    List<PermissionDTO> fetchPermissions();

    PermissionDTO fetchPermissionById(Long id) throws ResourceNotFoundException;

    PermissionDTO addPermission(PermissionDTO permission);

    PermissionDTO updatePermission(PermissionDTO permission) throws ResourceNotFoundException;

    /**
     * 删除权限以及其子权限
     *
     * @param id 权限id
     * @throws ResourceNotFoundException 权限不存在
     */
    void removePermission(Long id) throws ResourceNotFoundException;
}
