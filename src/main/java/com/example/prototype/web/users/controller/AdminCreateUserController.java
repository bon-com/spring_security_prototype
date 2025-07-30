package com.example.prototype.web.users.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.prototype.base.common.constants.Constants;
import com.example.prototype.biz.users.service.AuthorityService;
import com.example.prototype.biz.users.service.UsersService;
import com.example.prototype.web.users.dto.AuthorityMasterDto;
import com.example.prototype.web.users.dto.UsersForm;

@Controller
@RequestMapping("admin")
public class AdminCreateUserController {
    @Autowired
    private UsersService userService;
    
    @Autowired
    private AuthorityService authorityService; 
    
    @ModelAttribute
    public UsersForm setUpUsersForm() {
        return new UsersForm();
    }
    
    @ModelAttribute("authorityList")
    public List<AuthorityMasterDto> setUpAuthorityList() {
        return authorityService.findAllActive();
    }

    /**
     * 利用者情報の一覧画面表示
     * @param model
     * @return
     */
    @GetMapping(value = "/users")
    public String users(Model model, @RequestParam(name = "msgKey", required = false) String msgKey) {
        if (Constants.UPDATE_SUCCESS_KEY.equals(msgKey)) {
            // 更新メッセージ制御
            model.addAttribute("message", Constants.MSG_UPDATE_SUCCESS);
        } else if (Constants.INSERT_SUCCESS_KEY.equals(msgKey)) {
            // 登録メッセージ制御
            model.addAttribute("message", Constants.MSG_INSERT_SUCCESS);
        }
        
        model.addAttribute("userList", userService.findAll());
        return "admin/admin_users_overview";
    }

    /**
     * 利用者新規登録画面表示
     * @param model
     * @return
     */
    @GetMapping(value = "/users/register")
    public String register(Model model, @RequestParam(name = "msgKey", required = false) String msgKey) {
        if (Constants.UPDATE_SUCCESS_KEY.equals(msgKey)) {
            // 更新メッセージ制御
            model.addAttribute("message", Constants.MSG_UPDATE_SUCCESS);
        } else if (Constants.INSERT_SUCCESS_KEY.equals(msgKey)) {
            // 登録メッセージ制御
            model.addAttribute("message", Constants.MSG_INSERT_SUCCESS);
        }
        return "admin/admin_create_user";
    }
    
    /**
     * 利用者新規登録
     * @param form
     * @param rs
     * @param model
     * @return
     */
    @PostMapping(value = "/users/register")
    public String registerReq(@Valid UsersForm form, BindingResult rs, Model model) {
        if (rs.hasErrors()) {
            return "admin/admin_create_user";
        }
        
        // 利用者登録
        userService.insertUser(form);
        return "redirect:/admin/users?msgKey=" + Constants.INSERT_SUCCESS_KEY;
    }
}
