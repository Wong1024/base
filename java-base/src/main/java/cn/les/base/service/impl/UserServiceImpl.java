package cn.les.base.service.impl;

import cn.les.base.dto.UserDTO;
import cn.les.base.entity.UserDO;
import cn.les.base.entity.UserRoleDO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.repository.IUserDao;
import cn.les.base.repository.IUserRoleDao;
import cn.les.base.service.IUserService;
import cn.les.base.utils.SnowflakeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserRoleDao userRoleDao;

    @Override
    public UserDTO fetchUserById(Long id) throws ResourceNotFoundException {
        Optional<UserDO> userOptional = userDao.findByIdAndDeleteAtEquals(id, 0L);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("找不到用户！");
        }
        UserDO userDO = userOptional.get();
        userDO.setPassword(null);
        UserDTO user = UserDTO.fromUserDO(userDO);
        user.setRoles(
                userRoleDao.findByUserId(id)
                        .stream().map(UserRoleDO::getRoleId).map(Object::toString)
                        .collect(Collectors.toList())
        );
        return user;
    }

    @Override
    public UserDTO fetchUserByUsername(String username) throws ResourceNotFoundException {
        Optional<UserDO> userOptional = userDao.findByUsernameAndDeleteAtEquals(username, 0L);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("找不到用户！");
        }
        UserDO userDO = userOptional.get();
        userDO.setPassword(null);
        return UserDTO.fromUserDO(userDO);
    }

    @Override
    public Page<UserDTO> fetchUserPage(Pageable pageable) {
        return userDao.findWithPage(pageable);
    }

    @Override
    public List<UserDTO> fetchUsers(Sort sort) {
        return userDao.findAll(sort)
                .stream().map(UserDTO::fromUserDO)
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserDTO user) {
        UserDO userDO = user.toUserDO();
        userDO.setId(SnowflakeUtils.genId());
        userDO.setPassword(new BCryptPasswordEncoder().encode(userDO.getPassword()));
        userDO.setDeleteAt(0L);
        userDao.save(userDO);
        if (user.getRoles().size() > 0) {
            userRoleDao.saveAll(user.getRoles().stream().map(roleId -> {
                UserRoleDO userRole = new UserRoleDO();
                userRole.setUserId(userDO.getId());
                userRole.setRoleId(Long.parseLong(roleId));
                return userRole;
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public void updateUser(UserDTO user) throws ResourceNotFoundException {
        Optional<UserDO> userDOOptional = userDao.findByIdAndDeleteAtEquals(user.getId(), 0L);
        if (userDOOptional.isEmpty()) {
            throw new ResourceNotFoundException("找不到用户");
        }
        UserDO userDO = userDOOptional.get();
        BeanUtils.copyProperties(user, userDO, "password");
        if (!StringUtils.isEmpty(user.getPassword())) {
            userDO.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userDao.save(userDO);
        //对现有角色和提交的角色做交集，获得要删除的角色和要新增的角色，减少对数据库的操作
        List<Long> roleIds = user.getRoles() != null
                ? user.getRoles().stream().map(Long::parseLong).collect(Collectors.toList())
                : new ArrayList<>();
        Set<Long> currentRoleIds = userRoleDao.findByUserId(userDO.getId())
                .stream().map(UserRoleDO::getRoleId).collect(Collectors.toSet());
        Set<Long> newRoleIds = new HashSet<>(roleIds);
        Set<Long> commonIds = new HashSet<>();
        for (Long roleId : currentRoleIds) {
            if (newRoleIds.contains(roleId)) {
                commonIds.add(roleId);
            }
        }
        currentRoleIds.removeIf(commonIds::contains);
        newRoleIds.removeIf(commonIds::contains);
        if (currentRoleIds.size() > 0) {
            userRoleDao.deleteByRoleIdIn(new ArrayList<>(currentRoleIds));
        }
        if (newRoleIds.size() > 0) {
            userRoleDao.saveAll(newRoleIds.stream().map(roleId -> {
                UserRoleDO userRole = new UserRoleDO();
                userRole.setId(SnowflakeUtils.genId());
                userRole.setUserId(userDO.getId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public void updateUserState(Long id, Integer state) throws ResourceNotFoundException {
        Optional<UserDO> userDOOptional = userDao.findByIdAndDeleteAtEquals(id, 0L);
        if (userDOOptional.isEmpty()) {
            throw new ResourceNotFoundException("找不到用户");
        }
        UserDO userDO = userDOOptional.get();
        userDO.setState(state);
        userDao.save(userDO);
    }

    @Override
    public void removeUser(Long id) throws ResourceNotFoundException {
        Optional<UserDO> userDOOptional = userDao.findByIdAndDeleteAtEquals(id, 0L);
        if (userDOOptional.isEmpty()) {
            throw new ResourceNotFoundException("找不到用户");
        }
        UserDO userDO = userDOOptional.get();
        userDO.setDeleteAt(new Date().getTime());
        userDao.save(userDO);
    }
}
