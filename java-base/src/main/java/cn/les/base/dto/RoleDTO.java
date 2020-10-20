package cn.les.base.dto;

import cn.les.base.entity.RoleDO;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String roleName;

    public static RoleDTO fromRoleDO(RoleDO roleDO) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(roleDO.getId());
        roleDTO.setRoleName(roleDO.getRoleName());
        return roleDTO;
    }

    public RoleDO toRoleDO() {
        RoleDO role = new RoleDO();
        role.setRoleName(this.getRoleName());
        return role;
    }
}
