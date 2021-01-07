package cn.les.base.controller;

import cn.les.base.dto.MenuDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IMenuService;
import cn.les.base.utils.RequestResult;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MenuController {
    @Resource
    private IMenuService menuService;

    @GetMapping("/menus/{id}")
    public RequestResult fetchMenuById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return RequestResult.ok(menuService.fetchMenuById(id));
    }

    @GetMapping("/menus")
    public RequestResult fetchAllMenus() {
        return RequestResult.ok(menuService.fetchMenus());
    }

    @PostMapping("/menus")
    public RequestResult addMenu(@RequestBody MenuDTO menu) {
        ValidatorUtils.getInstance().notBlank(menu.getMenuName(), "菜单名不能为空")
                .notNull(menu.getParentId(), "父菜单不能为空")
                .notNull(menu.getType(), "菜单类型不能为空")
                .check(menu.getSort() != null && menu.getSort() >= 0, "排序顺序不能为空或负数");
        return RequestResult.ok(menuService.addMenu(menu));
    }

    @PutMapping("/menus/{id}")
    public RequestResult updateMenu(@PathVariable("id") Long id, @RequestBody MenuDTO menu) throws ResourceNotFoundException {
        ValidatorUtils.getInstance().notBlank(menu.getMenuName(), "菜单名不能为空")
                .notNull(menu.getParentId(), "父菜单不能为空")
                .notNull(menu.getType(), "菜单类型不能为空")
                .check(menu.getSort() != null && menu.getSort() >= 0, "排序顺序不能为空或负数");
        menu.setId(id);
        return RequestResult.ok(menuService.updateMenu(menu));
    }

    @DeleteMapping("/menus/{id}")
    public RequestResult removeMenu(@PathVariable("id") Long id) throws ResourceNotFoundException {
        menuService.removeMenu(id);
        return RequestResult.ok();
    }

    @GetMapping("/menus/{id}/permissionIds")
    public RequestResult fetchMenuIdsByRoleId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return RequestResult.ok(menuService.fetchPermissionIdsByMenuId(id));
    }

    @PutMapping("/menus/{id}/permissionIds")
    public RequestResult updateRoleMenusByRoleId(@PathVariable("id") Long id, @RequestBody List<Long> permissionIds)
            throws ResourceNotFoundException {
        return RequestResult.ok(menuService.updateMenuPermissionByMenuId(id, permissionIds));
    }
}
