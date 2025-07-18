package com.example.prototype.biz.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.security.dao.JdbcUsersDao;
import com.example.prototype.security.entity.Users;

/**
 * 認証クラス
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcUsersDao jdbcUsersDao;

    /**
     * 認証用の情報を取得
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 氏名検索
        Users user = jdbcUsersDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // 権限取得
        List<GrantedAuthority> authorityList = jdbcUsersDao.getAuthorityList(username);

        // Spring Security提供のUserクラスを返す
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), // ログインID
                user.getPassword(), // パスワード
                user.isEnabled(), // enabled（アカウント有効可否）
                true, // accountNonExpired（アカウント有効期限切れ可否 ⇒ falseならログイン不可）
                true, // credentialsNonExpired（パスワード有効期限切れ可否 ⇒ falseならログイン不可）
                true, // accountNonLocked（アカウントロック可否 ⇒ falseならログイン不可）
                authorityList); // 権限リスト
    }

}
