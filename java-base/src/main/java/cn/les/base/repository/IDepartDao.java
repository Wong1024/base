package cn.les.base.repository;

import cn.les.base.entity.DepartDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDepartDao extends JpaRepository<DepartDO, Long> {
    Optional<DepartDO> findByIdAndDeleteFlagFalse(Long id);

    List<DepartDO> findByDeleteFlagFalse();
}
