package cn.les.base.service.impl;

import cn.les.base.dto.PermissionWithRoleDTO;
import cn.les.base.dto.RolePermissionDTO;
import cn.les.base.entity.PermissionDO;
import cn.les.base.repository.IPermissionDao;
import cn.les.base.utils.SystemVariable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限验证
 *
 * Author: wyz
 * Date: 2019/2/28
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Resource
    private IPermissionDao permissionDao;

    @SuppressWarnings("unchecked")
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        AntPathRequestMatcher matcher;

        List<PermissionWithRoleDTO> permissionWithRoles = (List<PermissionWithRoleDTO>)request.getSession().getAttribute(SystemVariable.PERMISSION_WITH_ROLE);
        if (permissionWithRoles == null) {
            permissionWithRoles = new ArrayList<>();
            List<PermissionDO> permissions = permissionDao.findAll();
            List<RolePermissionDTO> rolePermissions = permissionDao.findAllRolePermissions();
            for(PermissionDO permission : permissions) {
                PermissionWithRoleDTO permissionWithRole = new PermissionWithRoleDTO();
                permissionWithRole.setId(permission.getId());
                permissionWithRole.setPath(permission.getPath());
                permissionWithRole.setMethod(permission.getMethod());
                Set<Long> roleIds = new HashSet<>();
                Iterator<RolePermissionDTO> iterator = rolePermissions.listIterator();
                while (iterator.hasNext()) {
                    RolePermissionDTO rolePermission = iterator.next();
                    if (permission.getId().equals(rolePermission.getPermissionId())) {
                        roleIds.add(rolePermission.getRoleId());
                        iterator.remove();
                    }
                }
                permissionWithRole.setRoleIds(roleIds);
                permissionWithRoles.add(permissionWithRole);
            }
            request.getSession().setAttribute(SystemVariable.PERMISSION_WITH_ROLE, permissionWithRoles);
        }

        //检查功能权限
        Set<Long> roleIds = new HashSet<>();
        boolean isMatch = false;
        for(PermissionWithRoleDTO permission : permissionWithRoles) {
            if (!StringUtils.hasLength(permission.getPath())) {
                continue;
            }
            matcher = new AntPathRequestMatcher(permission.getPath(), permission.getMethod());
            if (matcher.matches(request)) {
                isMatch = true;
                roleIds.addAll(permission.getRoleIds());
            }
        }
        if (isMatch) {
            roleIds.add(0L);
            return roleIds
                    .stream()
                    .map(roleId -> new SecurityConfig(roleId.toString()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
