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
     * 利用者登録更新
     * @param user
     */
    public void save(ExtendedUser user) {
        jdbcUsersDao.save(user);
    }
    
    /**
     * 利用者検索
     * @param username
     * @return
     */
    public ExtendedUser findByUsername(String username) {
        return jdbcUsersDao.findByUsername(username);
    }
}
