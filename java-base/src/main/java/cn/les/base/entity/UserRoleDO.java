package cn.les.base.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_user_role")
@Data
public class UserRoleDO {
    @Id
    private Long id;
    private Long userId;
    private Long roleId;
}
