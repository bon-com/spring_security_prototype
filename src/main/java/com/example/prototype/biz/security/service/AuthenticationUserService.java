package com.example.prototype.biz.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.security.dao.JdbcUsersDao;

/**
 * 認証クラス
 */
@Service("userDetailsService")
public class AuthenticationUserService implements UserDetailsService {

    @Autowired
    private JdbcUsersDao jdbcUsersDao;

    /**
     * 認証用の情報を取得（Spring Securityで呼ばれる）
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 氏名検索
        return jdbcUsersDao.findByUsername(username);
    }

}
