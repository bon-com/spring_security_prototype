package com.example.prototype.biz.security.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.prototype.security.entity.ExtendedUser;

@Repository
public class JdbcUsersDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /** エンティティマッパー */
    private static final ResultSetExtractor<ExtendedUser> userExtractor = rs -> {
        if (!rs.next()) {
            throw new UsernameNotFoundException("User not found");
        }

        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean enabled = rs.getBoolean("enabled");
        boolean accountNonLocked = rs.getBoolean("account_non_locked");
        int failureCount = rs.getInt("login_failure_count");

        Timestamp lastLoginTs = rs.getTimestamp("last_login_at");
        LocalDateTime lastLoginAt = (lastLoginTs != null) ? lastLoginTs.toLocalDateTime() : null;

        List<GrantedAuthority> authorities = new ArrayList<>();
        do {
            String authority = rs.getString("authority");
            if (authority != null) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        } while (rs.next());

        return new ExtendedUser(
            username, // ログインID
            password, // パスワード
            enabled, // enabled（アカウント有効可否）
            true, // accountNonExpired（アカウント有効期限切れ可否 ⇒ falseならログイン不可）
            true, // credentialsNonExpired（パスワード有効期限切れ可否 ⇒ falseならログイン不可）
            accountNonLocked, // accountNonLocked（アカウントロック可否 ⇒ falseならログイン不可）
            authorities, // 権限リスト
            failureCount, // ログイン失敗回数
            lastLoginAt // 最終ログイン日時
        );
    };

    /**
     * 利用者氏名検索
     * @param username
     * @return
     */
    public ExtendedUser findByUsername(String username) {
        var sql = "SELECT u.username as username, u.password as password, u.enabled as enabled, "
                + "u.account_non_locked as account_non_locked, u.login_failure_count as login_failure_count,"
                + " u.last_login_at as last_login_at, a.authority as authority FROM users u "
                + "INNER JOIN authorities a ON u.username = a.username WHERE u.username = :username";
        // パラメータ設定
        var param = new MapSqlParameterSource();
        param.addValue("username", username);

        return namedParameterJdbcTemplate.query(sql, param, userExtractor);
    }
    
    /**
     * 利用者情報登録更新
     * @param user
     */
    public void save(ExtendedUser user) {
        // 存在チェック
        String checkSql = "SELECT COUNT(*) FROM users WHERE username = :username";
        var param = new MapSqlParameterSource("username", user.getUsername());
        int count = namedParameterJdbcTemplate.queryForObject(checkSql, param, Integer.class);

        var beanParam = new BeanPropertySqlParameterSource(user);
        if (count > 0) {
            // UPDATE
            String updateSql = "UPDATE users SET password = :password, enabled = :enabled, "
                    + "account_non_locked = :accountNonLocked, login_failure_count = :loginFailureCount, "
                    + "last_login_at = :lastLoginAt WHERE username = :username";

            namedParameterJdbcTemplate.update(updateSql, beanParam);

        } else {
            // INSERT
            String insertSql = "INSERT INTO users (username, password, enabled, account_non_locked, "
                    + "login_failure_count, last_login_at) VALUES (:username, :password, :enabled, :accountNonLocked, "
                    + ":loginFailureCount, :lastLoginAt)";

            namedParameterJdbcTemplate.update(insertSql, beanParam);
        }
    }


}
