package cn.les.base.controller;

import cn.les.base.dto.UserDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IUserService;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 按id获得用户
     *
     * @param id 用户id
     * @return 用户信息
     * @throws ResourceNotFoundException 找不到用户
     */
    @GetMapping("/users/{id}")
    public UserDTO fetchUserById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        UserDTO user = userService.fetchUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("找不到用户！");
        }
        return user;
    }

    /**
     * 分页查询用户
     *
     * @param page   页码
     * @param size   每页条数
     * @param order  顺序
     * @param sortBy 排序字段
     * @return 分页用户数据
     */
    @GetMapping("/usersPage")
    public Page<UserDTO> fetchUserPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String order,
            String sortBy) {
        Sort sort = buildSort(order, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return userService.fetchUserPage(pageable);
    }

    /**
     * 查询用户
     *
     * @param order  顺序
     * @param sortBy 排序字段
     * @return 用户列表
     */
    @GetMapping("/users")
    public List<UserDTO> fetchUsers(String order, String sortBy) {
        Sort sort = buildSort(order, sortBy);
        return userService.fetchUsers(sort);
    }

    /**
     * 新增用户
     *
     * @param user 用户数据
     */
    @PostMapping("/users")
    public void addUser(@RequestBody UserDTO user) {
        ValidatorUtils.notBlank(user.getUsername(), "用户名不能为空");
        ValidatorUtils.notBlank(user.getNickname(), "姓名不能为空");
        ValidatorUtils.notBlank(user.getPassword(), "密码不能为空");
        userService.addUser(user);
    }

    /**
     * 修改用户
     *
     * @param user 用户数据
     * @param id   用户id
     * @throws ResourceNotFoundException 找不到用户
     */
    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody UserDTO user, @PathVariable("id") Long id) throws ResourceNotFoundException {
        ValidatorUtils.notBlank(user.getUsername(), "用户名不能为空");
        ValidatorUtils.notBlank(user.getNickname(), "姓名不能为空");
        user.setId(id);
        userService.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户状态
     * @param id   用户id
     * @throws ResourceNotFoundException 找不到用户
     */
    @PatchMapping("/users/{id}/state")
    public void updateUserState(@RequestBody UserDTO user, @PathVariable("id") Long id) throws ResourceNotFoundException {
        ValidatorUtils.notNull(user.getState(), "状态不能为空");
        userService.updateUserState(id, user.getState());
    }

    /**
     * 删除用户（修改标志位）
     *
     * @param id 用户id
     * @throws ResourceNotFoundException 找不到用户
     */
    @DeleteMapping("/users/{id}")
    public void removeUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.removeUser(id);
    }

    private Sort buildSort(String order, String sortBy) {
        List<String> sortFields = Arrays.asList("username", "nickname");
        Sort sort = Sort.unsorted();
        if (!StringUtils.isEmpty(sortBy) && sortFields.contains(sortBy)) {
            sort = Sort.by("desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }
        return sort;
    }
}
