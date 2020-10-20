package cn.les.base.service;

import cn.les.base.dto.MenuDTO;
import cn.les.base.exception.ResourceNotFoundException;

import java.util.List;

public interface IMenuService {
    List<MenuDTO> fetchMenuTreeByUserId(Long userId) throws ResourceNotFoundException;

    MenuDTO fetchMenuById(Long id) throws ResourceNotFoundException;

    List<MenuDTO> fetchMenus();

    void addMenu(MenuDTO menu);

    void updateMenu(MenuDTO menu) throws ResourceNotFoundException;

    void removeMenu(Long id) throws ResourceNotFoundException;

    List<Long> fetchPermissionIdsByMenuId(Long menuId) throws ResourceNotFoundException;

    void updateMenuPermissionByMenuId(Long menuId, List<Long> permissionIds);

}