package com.example.prototype.web.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.prototype.base.common.constants.Constants;
import com.example.prototype.biz.base.service.ItemService;

@Controller
@RequestMapping("admin/items")
public class AdminItemController {
    
    /** 商品サービス */
    @Autowired
    private ItemService itemService;

    /**
     * 商品一覧表示
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String items(Model model, @RequestParam(name = "msgKey", required = false) String msgKey) {
        // 商品一覧を取得
        model.addAttribute("items", itemService.findAllByAdmin());
        if (Constants.UPDATE_SUCCESS_KEY.equals(msgKey)) {
            model.addAttribute("message", Constants.UPDATE_SUCCESS_MSG);
        }
        
        return "security/admin_items";
    }
    
    /**
     * 商品を論理削除
     * @param id
     * @return
     */
    @GetMapping(value = "/update-deleted/{id}")
    public String deleteItem(@PathVariable int id, @RequestParam boolean deleted) {
        // 商品を削除
        itemService.updateDeleted(id, deleted);
        return "redirect:/admin/items/?msgKey=" + Constants.UPDATE_SUCCESS_KEY;
    }
}
