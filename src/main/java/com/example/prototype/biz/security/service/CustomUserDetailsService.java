package com.example.prototype.biz.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.security.dao.JdbcUserDao;
import com.example.prototype.security.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private JdbcUserDao jdbcUserDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = jdbcUserDao.findByUsername(username);
      if (user == null) {
          throw new UsernameNotFoundException("User not found");
      }
      
      List<GrantedAuthority> authorityList = jdbcUserDao.getAuthorityList(username);

      // Spring Securityの User クラスを返す
      return new org.springframework.security.core.userdetails.User(
        user.getUserName(),
        user.getPassword(),
        user.isEnabled(),         // enabled
        true,                     // accountNonExpired
        true,                     // credentialsNonExpired
        true,                     // accountNonLocked
        authorityList); // 権限リスト
    }
    
}
