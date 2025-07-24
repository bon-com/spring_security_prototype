package com.example.prototype.biz.security.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.prototype.base.common.constants.Constants;
import com.example.prototype.security.entity.ExtendedUser;

@Repository
public class JdbcUsersDao {
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(JdbcUsersDao.class);
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /** エンティティマッパー */
    private final ResultSetExtractor<ExtendedUser> userExtractor = rs -> {
        if (!rs.next()) {
            throw new UsernameNotFoundException(Constants.ERR_MSG_AUTHENTICATION_BAD_CREDENTIALS);
        }
        
        String loginId = rs.getString("login_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean enabled = rs.getBoolean("enabled");
        boolean accountNonLocked = rs.getBoolean("account_non_locked");
        int failureCount = rs.getInt("login_failure_count");
        Timestamp lastLoginTs = rs.getTimestamp("last_login_at");
        LocalDateTime lastLoginAt = (lastLoginTs != null) ? lastLoginTs.toLocalDateTime() : null;
        LocalDateTime accountExpiryAt = rs.getTimestamp("account_expiry_at").toLocalDateTime();

        List<GrantedAuthority> authorities = new ArrayList<>();
        do {
            String authority = rs.getString("authority");
            if (authority != null) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        } while (rs.next());

        return new ExtendedUser(
            loginId, // ログインID
            username, // 利用者氏名
            password, // パスワード
            enabled, // enabled（アカウント有効可否）
            true, // accountNonExpired（アカウント有効期限切れ可否 ⇒ falseならログイン不可）
            true, // credentialsNonExpired（パスワード有効期限切れ可否 ⇒ falseならログイン不可）
            accountNonLocked, // accountNonLocked（アカウントロック可否 ⇒ falseならログイン不可）
            authorities, // 権限リスト
            failureCount, // ログイン失敗回数
            lastLoginAt, // 最終ログイン日時
            accountExpiryAt // アカウント有効期限日時
        );
    };

    /**
     * ログインID検索
     * @param username
     * @return
     */
    public ExtendedUser findByLoginId(String loginId) {
        var sql = "SELECT u.login_id as login_id, u.username as username, u.password as password, u.enabled as enabled, "
                + "u.account_non_locked as account_non_locked, u.login_failure_count as login_failure_count,"
                + " u.last_login_at as last_login_at, u.account_expiry_at as account_expiry_at, a.authority as authority FROM users u "
                + "INNER JOIN authorities a ON u.login_id = a.login_id WHERE u.login_id = :loginId";
        var param = new MapSqlParameterSource();
        param.addValue("loginId", loginId);

        logger.debug("\n★★SQL実行★★\n・クラス=JdbcUsersDao\n・メソッド=findByLoginId\n・SQL={}\n・パラメータ={}\n", sql, param);
        return namedParameterJdbcTemplate.query(sql, param, userExtractor);
    }
    
    /**
     * 利用者情報登録更新
     * @param user
     */
    public void save(ExtendedUser user) {
        // 存在チェック
        var checkSql = "SELECT COUNT(*) FROM users WHERE login_id = :loginId";
        var param = new MapSqlParameterSource("loginId", user.getLoginId());
        
        logger.debug("\n★★SQL実行★★\n・クラス=JdbcUsersDao\n・メソッド=save\n・SQL={}\n・パラメータ={}\n", checkSql, param);
        int count = namedParameterJdbcTemplate.queryForObject(checkSql, param, Integer.class);

        var beanParam = new BeanPropertySqlParameterSource(user);
        if (count > 0) {
            // UPDATE
            var updateSql = "UPDATE users SET enabled = :enabled, "
                    + "account_non_locked = :accountNonLocked, login_failure_count = :loginFailureCount, "
                    + "last_login_at = :lastLoginAt WHERE login_id = :loginId";

            logger.debug("\n★★SQL実行★★\n・クラス=JdbcUsersDao\n・メソッド=save\n・SQL={}\n・パラメータ={}\n", updateSql, beanParam);
            namedParameterJdbcTemplate.update(updateSql, beanParam);

        } else {
            // INSERT
            var insertSql = "INSERT INTO users (login_id, username, password, enabled, account_non_locked, "
                    + "login_failure_count, last_login_at, account_expiry_at) VALUES (:loginId, :username, :password, :enabled, :accountNonLocked, "
                    + ":loginFailureCount, :lastLoginAt, :accountExpiryAt)";

            logger.debug("\n★★SQL実行★★\n・クラス=JdbcUsersDao\n・メソッド=save\n・SQL={}\n・パラメータ={}\n", insertSql, beanParam);
            namedParameterJdbcTemplate.update(insertSql, beanParam);
        }
    }


}
