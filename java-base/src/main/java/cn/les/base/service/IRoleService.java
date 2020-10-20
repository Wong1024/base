package cn.les.base.service;

import cn.les.base.dto.RoleDTO;
import cn.les.base.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IRoleService {
    RoleDTO fetchRoleById(Long id) throws ResourceNotFoundException;

    Page<RoleDTO> fetchRolePage(Pageable pageable);

    List<RoleDTO> fetchRoles(Sort sort);

    void addRole(RoleDTO role);

    void updateRole(RoleDTO role) throws ResourceNotFoundException;

    void removeRole(Long id) throws ResourceNotFoundException;

    List<Long> fetchMenuIdsByRoleId(Long roleId) throws ResourceNotFoundException;

    void updateRoleMenusByRoleId(Long roleId, List<Long> menuIds) throws ResourceNotFoundException;
}
