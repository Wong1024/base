package cn.les.base.mapstruct;

import cn.les.base.dto.MenuDTO;
import cn.les.base.entity.MenuDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuDTO menuDOtoMenuDTO(MenuDO menu);

    MenuDO menuDTOtoMenuDO(MenuDTO menu);

    void updateMenuFromDTO(MenuDTO menuDTO, @MappingTarget MenuDO menuDO);
}
