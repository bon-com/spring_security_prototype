package com.example.prototype.biz.base.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.prototype.base.entity.PurchaseHistory;

@Repository
public class JdbcPurchaseHistoryDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 購入履歴１件登録
     * @param purchaseHistory
     * @return
     */
    public int insert(PurchaseHistory purchaseHistory) {
        var sql = "INSERT INTO purchase_history(purchase_date) VALUES (:purchaseDate)";
        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(purchaseHistory);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, paramSource, keyHolder);
        // 生成されたキー（id）を取得
        return keyHolder.getKey().intValue();
    }

    /**
     * 購入日付一覧検索
     * @return
     */
    public List<LocalDate> findAllPurchaseDate() {
        var sql = "SELECT DISTINCT purchase_date FROM purchase_history";
        return namedParameterJdbcTemplate.query(sql, (rs, i) -> rs.getDate("purchase_date").toLocalDate());
    }

    /**
     * 購入日付をキーに購入履歴検索
     * @param purchaseDate
     * @return
     */
    public PurchaseHistory findByPurchaseDate(LocalDate purchaseDate) {
        var sql = "SELECT  h.id AS history_id, h.purchase_date, pi.id AS purchase_item_id, pi.purchase_id, pi.item_id, pi.quantity, pi.price, i.name AS item_name "
                + "FROM  purchase_history h JOIN purchase_item pi ON h.id = pi.purchase_id JOIN  item i ON pi.item_id = i.id WHERE  h.purchase_date = :purchaseDate ORDER BY h.id, pi.id";
        // パラメータ設定
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("purchaseDate", purchaseDate);

        // 実行
        return namedParameterJdbcTemplate.query(sql, param, new PurchaseHistoryExtractor());
    }

}
