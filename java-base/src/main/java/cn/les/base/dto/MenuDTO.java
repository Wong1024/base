package cn.les.base.dto;

import cn.les.base.entity.MenuDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class MenuDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String menuName;
    private String description;
    private String path;
    private String extraPath;
    private String menuIcon;
    private Integer sort;
    private Boolean newWindow;
    private List<MenuDTO> children;
}
