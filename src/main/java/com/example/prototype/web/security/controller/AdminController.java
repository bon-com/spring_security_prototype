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
import com.example.prototype.biz.security.service.UsersService;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private UsersService userService;

    /**
     * 商品一覧表示
     * @param model
     * @return
     */
    @GetMapping(value = "/items")
    public String items(Model model, @RequestParam(name = "msgKey", required = false) String msgKey) {
        // 商品一覧を取得
        model.addAttribute("items", itemService.findAllByAdmin());
        if (Constants.UPDATE_SUCCESS_KEY.equals(msgKey)) {
            model.addAttribute("message", Constants.MSG_UPDATE_SUCCESS);
        }
        
        return "admin/admin_items";
    }
    
    /**
     * 商品を更新
     * @param id
     * @return
     */
    @GetMapping(value = "/items/update-deleted/{id}")
    public String updateDeleted(@PathVariable int id, @RequestParam boolean deleted) {
        // 商品の削除フラグを更新
        itemService.updateDeletedByAdmin(id, deleted);
        return "redirect:/admin/items?msgKey=" + Constants.UPDATE_SUCCESS_KEY;
    }
    
    /**
     * 利用者情報の一覧表示
     * @param model
     * @return
     */
    @GetMapping(value = "/users")
    public String users(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "admin/admin_users_overview";
    }
}
