package com.example.prototype.biz.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.prototype.base.entity.PurchaseItem;

@Repository
public class JdbcPurchaseItemDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /** エンティティマッパー */
    private static final RowMapper<PurchaseItem> purHistoryRowMapper = (rs, i) -> {
        var purchaseItem = new PurchaseItem();
        purchaseItem.setId(rs.getInt("id"));
        purchaseItem.setPurchaseId(rs.getInt("purchase_id"));
        purchaseItem.setItemId(rs.getInt("item_id"));
        purchaseItem.setQuantity(rs.getInt("quantity"));
        purchaseItem.setPrice(rs.getInt("price"));
        purchaseItem.setItemName(rs.getString("item_name"));
        return purchaseItem;
    };

    /**
     * 購入商品履歴１件登録
     * @param purchaseItem
     */
    public void insert(PurchaseItem purchaseItem) {
        var sql = "INSERT INTO purchase_item (purchase_id, item_id, quantity, price) VALUES (:purchaseId, :itemId, :quantity, :price);";
        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(purchaseItem);

        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    /**
     * IDをキーに購入商品履歴を検索
     * @param purchaseId
     * @return
     */
    public List<PurchaseItem> findByPurchaseId(int purchaseId) {
        var sql = "SELECT p.id, p.purchase_id, p.item_id, p.quantity, p.price, i.name AS item_name FROM purchase_item p JOIN item i ON p.item_id = i.id WHERE p.purchase_id = :purchaseId ";

        // パラメータ設定
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("purchaseId", purchaseId);

        // 実行
        return namedParameterJdbcTemplate.query(sql, param, purHistoryRowMapper);
    }
}
