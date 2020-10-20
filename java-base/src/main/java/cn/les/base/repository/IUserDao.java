package cn.les.base.repository;

import cn.les.base.dto.UserDTO;
import cn.les.base.entity.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserDao extends JpaRepository<UserDO, Long> {
    Optional<UserDO> findByUsernameAndDeleteAtEquals(String username, Long deleteAt);

    Optional<UserDO> findByIdAndDeleteAtEquals(Long id, Long deleteAt);

    @Query(value = "select new cn.les.base.dto.UserDTO(" +
            "user.id, user.departId, depart.departName, user.username, user.nickname, user.state ) " +
            "from UserDO user left join DepartDO depart on user.departId = depart.id " +
            "where user.deleteAt = 0")
    Page<UserDTO> findWithPage(Pageable pageable);
}
