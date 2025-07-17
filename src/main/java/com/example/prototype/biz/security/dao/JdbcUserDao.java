package com.example.prototype.biz.security.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.example.prototype.security.entity.User;

@Repository
public class JdbcUserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /** エンティティマッパー */
    private static final RowMapper<User> userRowMapper = (rs, i) -> {
        var user = new User();
        user.setUserName(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    };

    public User findByUsername(String username) {
        var sql = "SELECT * FROM user WHERE username = :username";
        // パラメータ設定
        var param = new MapSqlParameterSource();
        param.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(sql, param, userRowMapper);
    }

    public List<GrantedAuthority> getAuthorityList(String username) {
        var authSql = "SELECT authority FROM authorities WHERE username = :username";
        // パラメータ設定
        var param = new MapSqlParameterSource();
        param.addValue("username", username);
        
        return namedParameterJdbcTemplate.query(authSql, param,
                (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString("authority")));
    }

}
