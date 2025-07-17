package com.example.prototype.web.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.prototype.biz.utils.MessageUtil;

@Controller
public class WelcomeController {
    
    /** メッセージユーティリティ */
    @Autowired
    private MessageUtil messageUtil;
    
    @GetMapping(value = "/")
    public String top(Model model) {
        // プロパティからメッセージ取得
        model.addAttribute("greeting", messageUtil.getMessage("welcome.msg"));
        return "base/top";
    }
}
