package cn.les.base.mapstruct;

import cn.les.base.dto.DepartDTO;
import cn.les.base.entity.DepartDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartMapper {
    DepartDTO departDOtoDepartDTO(DepartDO departDO);

    DepartDO departDTOtoDepartDO(DepartDTO departDTO);
}
