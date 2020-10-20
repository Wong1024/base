package cn.les.base.dto;

import cn.les.base.entity.DepartDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class DepartDTO {
    private Long id;
    private Long parentId;
    private String departName;

    public static DepartDTO fromDepartDO(DepartDO departDO) {
        DepartDTO depart = new DepartDTO();
        BeanUtils.copyProperties(departDO, depart);
        return depart;
    }

    public DepartDO toDepartDO() {
        DepartDO departDO = new DepartDO();
        BeanUtils.copyProperties(this, departDO);
        return departDO;
    }
}
