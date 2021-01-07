package cn.les.base.mapstruct;

import cn.les.base.dto.UserDTO;
import cn.les.base.entity.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO userDOtoUserDTO(UserDO user);

    UserDO userDTOtoUserDO(UserDTO user);

    @Mapping(target = "password", ignore = true)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget UserDO userDO);
}
