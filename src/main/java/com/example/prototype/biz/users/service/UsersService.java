package com.example.prototype.biz.users.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.userrs.dao.JdbcUsersDao;
import com.example.prototype.biz.users.entity.ExtendedUser;
import com.example.prototype.web.users.dto.UsersDto;

@Service
public class UsersService {
    @Autowired
    private JdbcUsersDao jdbcUsersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** パスワード有効期間 */
    @Value("${auth.password.expiry.period.days}")
    private int passwordExpiryPeriodDays;

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

    /**
     * 認証情報更新
     * @param user
     */
    public void updatePassword(ExtendedUser user) {
        // パスワードのハッシュ化
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // パスワード有効期間更新
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(passwordExpiryPeriodDays);
        user.setPasswordExpiryAt(expiryDate);

        jdbcUsersDao.updatePassword(user);
    }

    /**
     * 利用者一覧取得
     * @return
     */
    public List<UsersDto> findAll() {
        List<UsersDto> userList = new ArrayList<>();

        jdbcUsersDao.findAll().forEach(exUser -> {
            var dto = new UsersDto();
            BeanUtils.copyProperties(exUser, dto);
            userList.add(dto);
        });
        
        return userList;
    }
}
