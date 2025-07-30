package com.example.prototype.biz.users.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.prototype.biz.users.entity.Authorities;

@Repository
public class JdbcAuthoritiesDao {
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(JdbcAuthoritiesDao.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /**
     * 権限紐づきテーブル登録
     * @param authorities
     */
    public void insert(Authorities authorities) {
        var sql = new StringBuilder();
        sql.append("INSERT INTO authorities (");
        sql.append("login_id, authority_id");
        sql.append(") VALUES (");
        sql.append(":loginId, :authorityId)");
        var param = new BeanPropertySqlParameterSource(authorities);
        
        logger.debug(
                "\n★★SQL実行★★\n・クラス=JdbcAuthoritiesDao\n・メソッド=insert\n・SQL={}\n・パラメータ={}\n",
                sql, authorities);
        namedParameterJdbcTemplate.update(sql.toString(), param);
    }
}
