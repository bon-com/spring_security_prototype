package com.example.prototype.web.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.prototype.base.common.constants.Constants;
import com.example.prototype.biz.utils.MessageUtil;

@Controller
public class WelcomeController {
    @Autowired
    private MessageUtil messageUtil;
    
    /**
     * TOP画面表示
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String top(Model model) {
        // プロパティからメッセージ取得
        model.addAttribute("greeting", messageUtil.getMessage(Constants.WELCOME_MSG_KEY));
        return "base/top";
    }
}
