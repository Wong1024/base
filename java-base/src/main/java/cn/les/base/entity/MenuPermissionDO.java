package cn.les.base.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_menu_permission")
@Data
public class MenuPermissionDO {
    @Id
    private Long id;
    private Long menuId;
    private Long permissionId;
}
