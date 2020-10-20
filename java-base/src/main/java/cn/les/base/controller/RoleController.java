package cn.les.base.controller;

import cn.les.base.dto.RoleDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IRoleService;
import cn.les.base.utils.SortUtils;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoleController {
    @Resource
    private IRoleService roleService;

    /**
     * 按id获得角色
     *
     * @param id 角色id
     * @return 角色信息
     * @throws ResourceNotFoundException 找不到角色
     */
    @GetMapping("/roles/{id}")
    public RoleDTO fetchRoleById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        RoleDTO role = roleService.fetchRoleById(id);
        if (role == null) {
            throw new ResourceNotFoundException("找不到角色！");
        }
        return role;
    }

    /**
     * 分页查询角色
     *
     * @param page   页码
     * @param size   每页条数
     * @param order  顺序
     * @param sortBy 排序字段
     * @return 分页角色数据
     */
    @GetMapping("/rolesPage")
    public Page<RoleDTO> fetchRolePage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String order,
            String sortBy) {
        Sort sort = SortUtils.buildSort(order, sortBy, new String[]{"roleName"});
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return roleService.fetchRolePage(pageable);
    }

    /**
     * 查询角色
     *
     * @param order  顺序
     * @param sortBy 排序字段
     * @return 角色列表
     */
    @GetMapping("/roles")
    public List<RoleDTO> fetchRoles(String order, String sortBy) {
        Sort sort = SortUtils.buildSort(order, sortBy, new String[]{"roleName"});
        return roleService.fetchRoles(sort);
    }

    /**
     * 新增角色
     *
     * @param role 角色数据
     */
    @PostMapping("/roles")
    public void addRole(@RequestBody RoleDTO role) {
        ValidatorUtils.notBlank(role.getRoleName(), "角色名不能为空");
        roleService.addRole(role);
    }

    /**
     * 修改角色
     *
     * @param role 角色数据
     * @param id   角色id
     * @throws ResourceNotFoundException 找不到角色
     */
    @PutMapping("/roles/{id}")
    public void updateRole(@RequestBody RoleDTO role, @PathVariable("id") Long id) throws ResourceNotFoundException {
        ValidatorUtils.notBlank(role.getRoleName(), "角色名不能为空");
        role.setId(id);
        roleService.updateRole(role);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @throws ResourceNotFoundException 找不到角色
     */
    @DeleteMapping("/roles/{id}")
    public void removeRole(@PathVariable("id") Long id) throws ResourceNotFoundException {
        roleService.removeRole(id);
    }

    @GetMapping("/roles/{id}/menuIds")
    public List<Long> fetchMenuIdsByRoleId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return roleService.fetchMenuIdsByRoleId(id);
    }

    @PutMapping("/roles/{id}/menuIds")
    public void updateRoleMenusByRoleId(@PathVariable("id") Long id, @RequestBody List<Long> menuIds)
            throws ResourceNotFoundException {
        roleService.updateRoleMenusByRoleId(id, menuIds);
    }
}
