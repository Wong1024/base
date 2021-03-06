package cn.les.base.dto;

import cn.les.base.entity.PermissionDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class PermissionDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String permissionName;
    private String path;
    private String method;
    private Integer sort;
}
