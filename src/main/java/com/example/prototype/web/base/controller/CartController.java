package com.example.prototype.web.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.prototype.biz.base.service.CartService;
import com.example.prototype.biz.base.service.ItemService;
import com.example.prototype.web.base.dto.CartAddForm;
import com.example.prototype.web.base.dto.CartDto;

@Controller
@RequestMapping("cart")
public class CartController {

    /** カート（セッション管理） */
    @Autowired
    private CartDto cart;
    /** 商品サービス */
    @Autowired
    private ItemService itemService;
    /** カートサービス */
    @Autowired
    private CartService cartService;

    /**
     * 商品をカートに追加する
     * @param form
     * @return
     */
    @PostMapping(value = "/add")
    public String addItem(CartAddForm form) {
        // 追加対象の商品が実在すれば追加
        var item = itemService.findById(form.getItemId());
        int quantity = form.getQuantity();
        if (item != null && quantity > 0) {
            cartService.addItem(cart, item, quantity);
        }

        return "redirect:/items/";
    }

    /**
     * 商品をカートから削除
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public String deleteItem(@PathVariable int id) {
        // カート（セッション）から対象商品を削除
        cartService.deleteItem(cart, id);
        return "redirect:/order/";
    }
}
