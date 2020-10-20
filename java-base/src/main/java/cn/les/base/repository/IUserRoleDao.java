package cn.les.base.repository;

import cn.les.base.entity.UserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRoleDao extends JpaRepository<UserRoleDO, Long> {
    List<UserRoleDO> findByUserId(Long userId);
    List<UserRoleDO> findByRoleId(Long roleId);
    void deleteByRoleIdIn(List<Long> roleIdList);
    void deleteByRoleId(Long roleId);
}
