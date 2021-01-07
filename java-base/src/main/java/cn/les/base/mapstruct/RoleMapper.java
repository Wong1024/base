package cn.les.base.mapstruct;

import cn.les.base.dto.RoleDTO;
import cn.les.base.entity.RoleDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO roleDOtoRoleDTO(RoleDO roleDO);

    RoleDO roleDTOtoRoleDO(RoleDTO roleDTO);
}
