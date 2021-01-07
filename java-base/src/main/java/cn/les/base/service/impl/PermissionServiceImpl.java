package cn.les.base.service.impl;

import cn.les.base.dto.PermissionDTO;
import cn.les.base.entity.PermissionDO;
import cn.les.base.entity.UserDO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.mapstruct.PermissionMapper;
import cn.les.base.repository.IMenuPermissionDao;
import cn.les.base.repository.IPermissionDao;
import cn.les.base.repository.IUserDao;
import cn.les.base.service.IPermissionService;
import cn.les.base.utils.SnowflakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Resource
    private IMenuPermissionDao menuPermissionDao;
    @Resource
    private IPermissionDao permissionDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> fetchLoginPermissionsByUserId(Long id) throws ResourceNotFoundException {
        Optional<UserDO> opt = userDao.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("用户不存在！");
        }
        if (opt.get().getDeleteAt() > 0) {
            throw new ResourceNotFoundException("用户已被删除！");
        }
        return permissionDao.findAllByUserIdWithoutGroup(id)
                .stream()
                .map(permissionDO -> permissionMapper.permissionDOtoPermissionDTO(permissionDO))
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> fetchPermissions() {
        return permissionDao.findAll()
                .stream()
                .map(permissionDO -> permissionMapper.permissionDOtoPermissionDTO(permissionDO))
                .collect(Collectors.toList());
    }

    @Override
    public PermissionDTO fetchPermissionById(Long id) throws ResourceNotFoundException {
        Optional<PermissionDO> opt = permissionDao.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("权限不存在！");
        }
        return permissionMapper.permissionDOtoPermissionDTO(opt.get());
    }

    @Override
    public PermissionDTO addPermission(PermissionDTO permission) {
        PermissionDO permissionDO = permissionMapper.permissionDTOtoPermissionDO(permission);
        permissionDO.setId(SnowflakeUtils.genId());
        permissionDao.save(permissionDO);
        return permissionMapper.permissionDOtoPermissionDTO(permissionDO);
    }

    @Override
    public PermissionDTO updatePermission(PermissionDTO permission) throws ResourceNotFoundException {
        Optional<PermissionDO> optional = permissionDao.findById(permission.getId());
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("权限不存在！");
        }
        PermissionDO permissionDO = optional.get();
        permissionMapper.updatePermissionFromDTO(permission, permissionDO);
        permissionDao.save(permissionDO);
        return permissionMapper.permissionDOtoPermissionDTO(permissionDO);
    }

    @Override
    public void removePermission(Long id) throws ResourceNotFoundException {
        if (!permissionDao.existsById(id)) {
            throw new ResourceNotFoundException("权限不存在！");
        }
        permissionDao.deleteById(id);
        List<PermissionDO> list = permissionDao.findAll();
        List<PermissionDO> children = fetchPermissionChildren(id, list);
        permissionDao.deleteAll(children);
        menuPermissionDao.deleteByPermissionId(id);
    }

    private List<PermissionDO> fetchPermissionChildren(Long parentId, List<PermissionDO> all) {
        List<PermissionDO> result = new ArrayList<>();
        Queue<PermissionDO> tmp = new LinkedList<>();
        Iterator<PermissionDO> it = all.iterator();
        PermissionDO permission, itemPermission;
        Long itemId;
        while(it.hasNext()) {
            permission = it.next();
            if (permission.getId().equals(parentId)) {
                it.remove();
            }
            if (permission.getParentId().equals(parentId)) {
                tmp.add(permission);
                it.remove();
            }
        }
        while(tmp.size() > 0) {
            itemPermission = tmp.poll();
            result.add(itemPermission);
            it = all.iterator();
            itemId = itemPermission.getId();
            while(it.hasNext()) {
                permission = it.next();
                if (permission.getParentId().equals(itemId)) {
                    tmp.add(permission);
                    it.remove();
                }
            }
        }

        return result;
    }
}
