package cn.les.base.dto;

import cn.les.base.entity.DepartDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class DepartDTO {
    private Long id;
    private Long parentId;
    private String departName;
}
