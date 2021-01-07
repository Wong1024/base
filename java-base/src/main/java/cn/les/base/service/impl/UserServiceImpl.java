package cn.les.base.service.impl;

import cn.les.base.dto.UserDTO;
import cn.les.base.entity.UserDO;
import cn.les.base.entity.UserRoleDO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.mapstruct.UserMapper;
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
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO fetchUserById(Long id) throws ResourceNotFoundException {
        Optional<UserDO> userOptional = userDao.findByIdAndDeleteAtEquals(id, 0L);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("找不到用户！");
        }
        UserDO userDO = userOptional.get();
        userDO.setPassword(null);
        UserDTO user = userMapper.userDOtoUserDTO(userDO);
        user.setRoles(fetchRoleIdsByUserId(id));
        return user;
    }

    @Override
    public UserDTO fetchUserByUsername(String username) throws ResourceNotFoundException {
        Optional<UserDO> userOptional = userDao.findByUsernameAndDeleteAtEquals(username, 0L);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("找不到用户！");
        }
        UserDO userDO = userOptional.get();
        userDO.setPassword(null);
        return userMapper.userDOtoUserDTO(userDO);
    }

    @Override
    public Page<UserDTO> fetchUserPage(Pageable pageable) {
        return userDao.findWithPage(pageable);
    }

    @Override
    public List<UserDTO> fetchUsers(Sort sort) {
        return userDao.findAll(sort)
                .stream().map(userDO -> userMapper.userDOtoUserDTO(userDO))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO addUser(UserDTO user) {
        UserDO userDO = userMapper.userDTOtoUserDO(user);
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
        UserDTO result = userMapper.userDOtoUserDTO(userDO);
        result.setRoles(fetchRoleIdsByUserId(result.getId()));
        return result;
    }

    @Override
    public UserDTO updateUser(UserDTO user) throws ResourceNotFoundException {
        Optional<UserDO> userDOOptional = userDao.findByIdAndDeleteAtEquals(user.getId(), 0L);
        if (!userDOOptional.isPresent()) {
            throw new ResourceNotFoundException("找不到用户");
        }
        UserDO userDO = userDOOptional.get();
        userMapper.updateUserFromDTO(user, userDO);
        if (StringUtils.hasLength(user.getPassword())) {
            userDO.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userDao.save(userDO);
        //对现有角色和提交的角色做交集，获得要删除的角色和要新增的角色，减少对数据库的操作
        Set<Long> newRoleIds = user.getRoles() != null
                ? user.getRoles().stream().map(Long::parseLong).collect(Collectors.toSet())
                : new HashSet<>();
        Set<Long> currentRoleIds = userRoleDao.findByUserId(userDO.getId())
                .stream().map(UserRoleDO::getRoleId).collect(Collectors.toSet());
        Set<Long> commonIds = currentRoleIds.stream()
                .filter(newRoleIds::contains)
                .collect(Collectors.toSet());
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
        UserDTO result = userMapper.userDOtoUserDTO(userDO);
        result.setRoles(fetchRoleIdsByUserId(result.getId()));
        return result;
    }

    @Override
    public UserDTO updateUserState(Long id, Integer state) throws ResourceNotFoundException {
        Optional<UserDO> optional = userDao.findByIdAndDeleteAtEquals(id, 0L);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("找不到用户");
        }
        UserDO userDO = optional.get();
        userDO.setState(state);
        userDao.save(userDO);
        UserDTO result = userMapper.userDOtoUserDTO(userDO);
        result.setRoles(fetchRoleIdsByUserId(result.getId()));
        return result;
    }

    @Override
    public void removeUser(Long id) throws ResourceNotFoundException {
        Optional<UserDO> userDOOptional = userDao.findByIdAndDeleteAtEquals(id, 0L);
        if (!userDOOptional.isPresent()) {
            throw new ResourceNotFoundException("找不到用户");
        }
        UserDO userDO = userDOOptional.get();
        userDO.setDeleteAt(new Date().getTime());
        userDao.save(userDO);
    }

    private List<String> fetchRoleIdsByUserId(Long userId) {
        return userRoleDao.findByUserId(userId)
                .stream().map(UserRoleDO::getRoleId).map(Object::toString)
                .collect(Collectors.toList());
    }
}
