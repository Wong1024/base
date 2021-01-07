package cn.les.base.service;

import cn.les.base.dto.UserDTO;
import cn.les.base.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IUserService {
    UserDTO fetchUserById(Long id) throws ResourceNotFoundException;

    UserDTO fetchUserByUsername(String username) throws ResourceNotFoundException;

    Page<UserDTO> fetchUserPage(Pageable pageable);

    List<UserDTO> fetchUsers(Sort sort);

    UserDTO addUser(UserDTO user);

    UserDTO updateUser(UserDTO user) throws ResourceNotFoundException;

    UserDTO updateUserState(Long id, Integer state) throws ResourceNotFoundException;

    void removeUser(Long id) throws ResourceNotFoundException;
}
