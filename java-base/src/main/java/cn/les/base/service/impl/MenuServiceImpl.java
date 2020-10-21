package cn.les.base.service.impl;

import cn.les.base.dto.MenuDTO;
import cn.les.base.entity.MenuDO;
import cn.les.base.entity.MenuPermissionDO;
import cn.les.base.entity.RoleMenuDO;
import cn.les.base.entity.UserDO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.repository.IMenuDao;
import cn.les.base.repository.IMenuPermissionDao;
import cn.les.base.repository.IUserDao;
import cn.les.base.service.IMenuService;
import cn.les.base.utils.SnowflakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements IMenuService {
    @Resource
    private IMenuDao menuDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IMenuPermissionDao menuPermissionDao;

    @Override
    public List<MenuDTO> fetchMenuTreeByUserId(Long userId) throws ResourceNotFoundException {
        Optional<UserDO> opt = userDao.findById(userId);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("用户不存在！");
        }
        if (opt.get().getDeleteAt() > 0) {
            throw new ResourceNotFoundException("用户已被删除！");
        }
        List<MenuDTO> result = new ArrayList<>();
        List<Long> ids = menuDao.findAllMenuIdsByUserId(userId);
        List<MenuDO> all = menuDao.findAllByOrderBySort();
        List<Long> sortIds = all.stream()
                .map(MenuDO::getId)
                .collect(Collectors.toList());
        Map<Long, MenuDTO> map = all.stream()
                .map(MenuDTO::fromMenuDO)
                .collect(Collectors.toMap(MenuDTO::getId, item -> item));

        MenuDTO item, parent;
        for (Long id : sortIds) {
            item = map.get(id);
            if (!ids.contains(id) || !map.containsKey(item.getParentId())) {
                continue;
            }
            boolean flag = true;
            parent = map.get(item.getParentId());
            while (flag) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(item);
                flag = map.containsKey(parent.getParentId());
                if (flag) {
                    parent = map.get(parent.getParentId());
                }
            }
        }
        for (Long id : sortIds) {
            item = map.get(id);
            if (item.getParentId() == 0) {
                if (item.getType() == 1
                        || (item.getType() == 0
                        && item.getChildren() != null
                        && item.getChildren().size() > 0)) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    @Override
    public MenuDTO fetchMenuById(Long id) throws ResourceNotFoundException {
        Optional<MenuDO> opt = menuDao.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("找不到菜单！");
        }
        return MenuDTO.fromMenuDO(opt.get());
    }

    @Override
    public List<MenuDTO> fetchMenus() {
        return menuDao.findAllByOrderBySort()
                .stream()
                .map(MenuDTO::fromMenuDO)
                .collect(Collectors.toList());
    }

    @Override
    public void addMenu(MenuDTO menu) {
        MenuDO menuDO = menu.toMenuDO();
        menuDO.setId(SnowflakeUtils.genId());
        menuDao.save(menuDO);
    }

    @Override
    public void updateMenu(MenuDTO menu) throws ResourceNotFoundException {
        if (!menuDao.existsById(menu.getId())) {
            throw new ResourceNotFoundException("找不到菜单！");
        }
        menuDao.save(menu.toMenuDO());
    }

    @Override
    public void removeMenu(Long id) throws ResourceNotFoundException {
        if (!menuDao.existsById(id)) {
            throw new ResourceNotFoundException("找不到菜单！");
        }
        menuDao.deleteById(id);
        menuPermissionDao.deleteByMenuId(id);
    }

    @Override
    public List<Long> fetchPermissionIdsByMenuId(Long menuId) throws ResourceNotFoundException {
        if (!menuDao.existsById(menuId)) {
            throw new ResourceNotFoundException("找不到菜单！");
        }
        return menuPermissionDao.findAllByMenuId(menuId)
                .stream()
                .map(MenuPermissionDO::getPermissionId)
                .collect(Collectors.toList());
    }

    @Override
    public void updateMenuPermissionByMenuId(Long menuId, List<Long> permissionIds) {
        Set<Long> existPermissionIds = menuPermissionDao.findAllByMenuId(menuId)
                .stream()
                .map(MenuPermissionDO::getPermissionId)
                .collect(Collectors.toSet());
        Set<Long> common = permissionIds.stream().filter(existPermissionIds::contains).collect(Collectors.toSet());
        existPermissionIds.removeIf(common::contains);
        permissionIds.removeIf(common::contains);
        menuPermissionDao.deleteByMenuIdEqualsAndPermissionIdIn(menuId, new ArrayList<>(existPermissionIds));

        List<MenuPermissionDO> newList = permissionIds.stream().map(id -> {
            MenuPermissionDO menuPermissionDO = new MenuPermissionDO();
            menuPermissionDO.setId(SnowflakeUtils.genId());
            menuPermissionDO.setMenuId(menuId);
            menuPermissionDO.setPermissionId(id);
            return menuPermissionDO;
        }).collect(Collectors.toList());
        menuPermissionDao.saveAll(newList);
    }
}
