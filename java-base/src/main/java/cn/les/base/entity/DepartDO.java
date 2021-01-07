package cn.les.base.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_depart")
@DynamicInsert
@DynamicUpdate
@Data
public class DepartDO {
    @Id
    private Long id;
    private Long parentId;
    private String departName;
    private Boolean deleteFlag;
}
