package cn.les.base.controller;

import cn.les.base.dto.MenuDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IMenuService;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MenuController {
    @Resource
    private IMenuService menuService;

    @GetMapping("/menus/{id}")
    MenuDTO fetchMenuById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return menuService.fetchMenuById(id);
    }

    @GetMapping("/menus")
    List<MenuDTO> fetchAllMenus() {
        return menuService.fetchMenus();
    }

    @PostMapping("/menus")
    void addMenu(@RequestBody MenuDTO menu) {
        ValidatorUtils.notBlank(menu.getMenuName(), "菜单名不能为空");
        ValidatorUtils.notNull(menu.getParentId(), "父菜单不能为空");
        ValidatorUtils.notNull(menu.getType(), "菜单类型不能为空");
        ValidatorUtils.check(menu.getSort() != null && menu.getSort() >= 0, "排序顺序不能为空或负数");
        menuService.addMenu(menu);
    }

    @PutMapping("/menus/{id}")
    void updateMenu(@PathVariable("id") Long id, @RequestBody MenuDTO menu) throws ResourceNotFoundException {
        ValidatorUtils.notBlank(menu.getMenuName(), "菜单名不能为空");
        ValidatorUtils.notNull(menu.getParentId(), "父菜单不能为空");
        ValidatorUtils.notNull(menu.getType(), "菜单类型不能为空");
        ValidatorUtils.check(menu.getSort() != null && menu.getSort() >= 0, "排序顺序不能为空或负数");
        menu.setId(id);
        menuService.updateMenu(menu);
    }

    @DeleteMapping("/menus/{id}")
    void removeMenu(@PathVariable("id") Long id) throws ResourceNotFoundException {
        menuService.removeMenu(id);
    }

    @GetMapping("/menus/{id}/permissionIds")
    public List<Long> fetchMenuIdsByRoleId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return menuService.fetchPermissionIdsByMenuId(id);
    }

    @PutMapping("/menus/{id}/permissionIds")
    public void updateRoleMenusByRoleId(@PathVariable("id") Long id, @RequestBody List<Long> permissionIds)
            throws ResourceNotFoundException {
        menuService.updateMenuPermissionByMenuId(id, permissionIds);
    }
}
