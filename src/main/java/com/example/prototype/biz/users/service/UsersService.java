package com.example.prototype.biz.users.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.users.dao.JdbcAuthoritiesDao;
import com.example.prototype.biz.users.dao.JdbcUsersDao;
import com.example.prototype.biz.users.entity.Authorities;
import com.example.prototype.biz.users.entity.ExtendedUser;
import com.example.prototype.web.users.dto.UsersDto;
import com.example.prototype.web.users.dto.UsersForm;

@Service
public class UsersService {
    @Autowired
    private JdbcUsersDao jdbcUsersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JdbcAuthoritiesDao jdbcAuthoritiesDao;

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
    
    /**
     * 利用者情報の登録
     * @param form
     */
    public void insertUser(UsersForm form) {
        // 登録エンティティ作成
        var user = ExtendedUser.builder()
                .loginId(form.getLoginId())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .enabled(form.isEnabled())
                .accountNonLocked(form.isAccountNonLocked())
                .accountExpiryAt(form.getAccountExpiryAt())
                .passwordExpiryAt(form.getPasswordExpiryAt())
                .build();
        
        // 利用者登録
        jdbcUsersDao.insert(user);
        // 権限登録
        form.getAuthorityIds().forEach(authorityId -> {
            var authority = new Authorities(form.getLoginId(), authorityId);
            jdbcAuthoritiesDao.insert(authority);
        });
    }
}
