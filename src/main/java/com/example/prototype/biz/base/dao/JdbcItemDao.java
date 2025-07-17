package com.example.prototype.biz.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.prototype.base.entity.Item;

@Repository
public class JdbcItemDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /** エンティティマッパー */
    private static final RowMapper<Item> itemRowMapper = (rs, i) -> {
        var item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getInt("price"));
        return item;
    };

    /**
     * 商品全件検索
     * @return
     */
    public List<Item> findAll() {
        var sql = "SELECT id, name, price FROM item";
        // 実行
        return namedParameterJdbcTemplate.query(sql, itemRowMapper);
    }

    /**
     * 商品ID検索
     * @param id
     * @return
     */
    public Item findById(int id) {
        var sql = "SELECT id, name, price FROM item WHERE id = :id";
        // パラメータ設定
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        // 実行
        return namedParameterJdbcTemplate.queryForObject(sql, param, itemRowMapper);
    }
}
