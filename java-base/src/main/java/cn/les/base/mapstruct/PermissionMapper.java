package cn.les.base.mapstruct;

import cn.les.base.dto.PermissionDTO;
import cn.les.base.entity.PermissionDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDTO permissionDOtoPermissionDTO(PermissionDO permissionDO);

    PermissionDO permissionDTOtoPermissionDO(PermissionDTO permissionDTO);

    void updatePermissionFromDTO(PermissionDTO permissionDTO, @MappingTarget PermissionDO permissionDO);
}
