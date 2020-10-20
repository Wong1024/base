package cn.les.base.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_role_menu")
@Data
public class RoleMenuDO {
    @Id
    private Long id;
    private Long roleId;
    private Long menuId;
}
