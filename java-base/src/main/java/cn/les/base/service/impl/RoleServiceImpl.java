package cn.les.base.service.impl;

import cn.les.base.dto.RoleDTO;
import cn.les.base.entity.RoleDO;
import cn.les.base.entity.RoleMenuDO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.repository.IRoleDao;
import cn.les.base.repository.IRoleMenuDao;
import cn.les.base.repository.IUserRoleDao;
import cn.les.base.service.IRoleService;
import cn.les.base.utils.SnowflakeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IUserRoleDao userRoleDao;
    @Resource
    private IRoleMenuDao roleMenuDao;

    @Override
    public RoleDTO fetchRoleById(Long id) throws ResourceNotFoundException {
        Optional<RoleDO> optional = roleDao.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("找不到角色！");
        }
        return RoleDTO.fromRoleDO(optional.get());
    }

    @Override
    public Page<RoleDTO> fetchRolePage(Pageable pageable) {
        return roleDao.findAll(pageable).map(RoleDTO::fromRoleDO);
    }

    @Override
    public List<RoleDTO> fetchRoles(Sort sort) {
        return roleDao.findAll()
                .stream().map(RoleDTO::fromRoleDO).collect(Collectors.toList());
    }

    @Override
    public void addRole(RoleDTO role) {
        RoleDO roleDO = role.toRoleDO();
        roleDO.setId(SnowflakeUtils.genId());
        roleDao.save(roleDO);
    }

    @Override
    public void updateRole(RoleDTO role) throws ResourceNotFoundException {
        Optional<RoleDO> optional = roleDao.findById(role.getId());
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("找不到角色！");
        }
        RoleDO roleDO = optional.get();
        roleDO.setRoleName(role.getRoleName());
        roleDao.save(roleDO);
    }

    @Override
    public void removeRole(Long id) throws ResourceNotFoundException {
        if (!roleDao.existsById(id)) {
            throw new ResourceNotFoundException("找不到角色！");
        }
        roleDao.deleteById(id);
        userRoleDao.deleteByRoleId(id);
    }

    @Override
    public List<Long> fetchMenuIdsByRoleId(Long roleId) throws ResourceNotFoundException {
        if (!roleDao.existsById(roleId)) {
            throw new ResourceNotFoundException("找不到角色！");
        }
        return roleMenuDao.findAllByRoleId(roleId)
                .stream()
                .map(RoleMenuDO::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public void updateRoleMenusByRoleId(Long roleId, List<Long> menuIds) throws ResourceNotFoundException {
        Set<Long> existMenuIds = roleMenuDao.findAllByRoleId(roleId)
                .stream()
                .map(RoleMenuDO::getMenuId)
                .collect(Collectors.toSet());
        Set<Long> common = menuIds.stream().filter(existMenuIds::contains).collect(Collectors.toSet());
        existMenuIds.removeIf(common::contains);
        menuIds.removeIf(common::contains);
        roleMenuDao.deleteByRoleIdEqualsAndMenuIdIn(roleId, new ArrayList<>(existMenuIds));

        List<RoleMenuDO> newList = menuIds.stream().map(id -> {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setId(SnowflakeUtils.genId());
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(id);
            return roleMenuDO;
        }).collect(Collectors.toList());
        roleMenuDao.saveAll(newList);
    }
}
