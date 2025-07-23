package com.example.prototype.biz.base.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.prototype.base.entity.Item;

@Repository
public class JdbcItemDao {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(JdbcItemDao.class);
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /** エンティティマッパー */
    private static final RowMapper<Item> itemRowMapper = (rs, i) -> {
        var item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getInt("price"));
        item.setDeleted(rs.getBoolean("deleted"));
        return item;
    };

    /**
     * 商品全件検索
     * @return
     */
    public List<Item> findAll() {
        var sql = "SELECT id, name, price, deleted FROM item WHERE deleted = false";
        // 実行
        return namedParameterJdbcTemplate.query(sql, itemRowMapper);
    }

    /**
     * 商品ID検索
     * @param id
     * @return
     */
    public Item findById(int id) {
        var sql = "SELECT id, name, price, deleted FROM item WHERE id = :id and deleted = false";
        // パラメータ設定
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        // 実行
        return namedParameterJdbcTemplate.queryForObject(sql, param, itemRowMapper);
    }
    
    /**
     * 商品削除
     * @param id
     */
    public void updateDeleted(int id, boolean deleted) {
        var sql = "UPDATE item SET deleted = :deleted WHERE id = :id";
        // パラメータ設定
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        param.addValue("deleted", deleted);
        // 実行
        int count = namedParameterJdbcTemplate.update(sql, param);
        if (count == 0) {
            logger.warn("\n★★商品テーブル更新失敗★★\n・SQL={}\n・パラメータ={}\n", sql, param);
            throw new IllegalStateException("更新対象が存在しません");
        }
    }
    

    /**
     * 商品全件検索
     * @return
     */
    public List<Item> findAllByAdmin() {
        var sql = "SELECT id, name, price, deleted FROM item";
        // 実行
        return namedParameterJdbcTemplate.query(sql, itemRowMapper);
    }
}
