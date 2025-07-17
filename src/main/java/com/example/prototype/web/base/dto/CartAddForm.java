package com.example.prototype.web.base.dto;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * カート追加リクエストフォーム
 */
@Data
public class CartAddForm {
    /** 商品ID */
    private int itemId;
    /** 数量 */
    @Size(min = 1, max = 10)
    private int quantity;
}
