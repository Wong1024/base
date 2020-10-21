package cn.les.base.service.impl;

import cn.les.base.dto.LoginUser;
import cn.les.base.entity.UserDO;
import cn.les.base.repository.IRoleDao;
import cn.les.base.repository.IUserDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Resource
    private IUserDao userDao;
    @Resource
    private IRoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDO> opt = userDao.findByUsernameAndDeleteAtEquals(username, 0L);
        if (!opt.isPresent()){
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserDO user = opt.get();

        Set<GrantedAuthority> grantedAuthorities = roleDao.findAll().stream()
                .map(role -> new SimpleGrantedAuthority(role.getId().toString()))
                .collect(Collectors.toSet());

        return LoginUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.getState() != 1)
                .authorities(Collections.unmodifiableSet(grantedAuthorities))
                .build();
    }
}
