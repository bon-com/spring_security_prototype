package com.example.prototype.biz.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.security.dao.JdbcUsersDao;
import com.example.prototype.security.entity.ExtendedUser;

@Service
public class UsersService {
    @Autowired
    private JdbcUsersDao jdbcUsersDao;
    
    /**
     * 認証情報更新
     * @param user
     */
    public void updateAuthStatus(ExtendedUser user) {
        jdbcUsersDao.updateAuthStatus(user);
    }
    
    /**
     * ログインID検索
     * @param username
     * @return
     */
    public ExtendedUser findByLoginId(String loginId) {
        return jdbcUsersDao.findByLoginId(loginId);
    }
}
