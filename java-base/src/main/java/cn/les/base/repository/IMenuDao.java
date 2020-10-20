package cn.les.base.repository;

import cn.les.base.entity.MenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMenuDao extends JpaRepository<MenuDO, Long> {
    List<MenuDO> findAllByOrderBySort();

    @Query(value = "select menu.id from MenuDO menu, UserRoleDO userRole, RoleMenuDO roleMenu " +
            "where menu.id = roleMenu.menuId and roleMenu.roleId = userRole.roleId " +
            "and userRole.userId = ?1 and menu.type = 1")
    List<Long> findAllMenuIdsByUserId(Long userId);
}
