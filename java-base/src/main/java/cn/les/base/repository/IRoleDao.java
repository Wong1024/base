package cn.les.base.repository;

import cn.les.base.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<RoleDO, Long> {
}
