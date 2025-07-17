package com.example.prototype.biz.utils;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class SessionCleaner {
    
    public void clearSession(HttpSession session) {
        if (session != null) {
            // カート情報クリア
            session.removeAttribute("scopedTarget.cart");
        }
    }
}
