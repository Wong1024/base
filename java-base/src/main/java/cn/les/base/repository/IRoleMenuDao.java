package cn.les.base.repository;

import cn.les.base.entity.RoleMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRoleMenuDao extends JpaRepository<RoleMenuDO, Long> {
    List<RoleMenuDO> findAllByRoleId(Long roleId);

    @Transactional
    void deleteByRoleIdEqualsAndMenuIdIn(Long roleId, List<Long> menuId);
}
