package cn.les.base.repository;

import cn.les.base.entity.MenuPermissionDO;
import cn.les.base.entity.RoleMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMenuPermissionDao extends JpaRepository<MenuPermissionDO, Long> {
    List<MenuPermissionDO> findAllByMenuId(Long menuId);

    @Transactional
    void deleteByMenuId(Long menuId);

    @Transactional
    void deleteByPermissionId(Long permissionId);

    @Transactional
    void deleteByMenuIdEqualsAndPermissionIdIn(Long menuId, List<Long> permissionId);
}
