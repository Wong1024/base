package cn.les.base.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PermissionWithRoleDTO {
    private Long id;
    private String path;
    private String method;
    private Set<Long> roleIds;
}
