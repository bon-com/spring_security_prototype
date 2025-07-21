package com.example.prototype.biz.security.listener;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.example.prototype.biz.security.service.UsersService;
import com.example.prototype.security.entity.ExtendedUser;

@Component
public class LoginSuccessEventListener {
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessEventListener.class);
    
    @Autowired
    private UsersService usersService;
    
    /** 認証成功後に呼ばれるイベントリスナー */
    @EventListener
    public void handleLoginSuccess(InteractiveAuthenticationSuccessEvent event) {
        // ログイン利用者存在確認
        String username = event.getAuthentication().getName();
        ExtendedUser user = usersService.findByUsername(username);
        if (user == null) {
            logger.error("認証成功後にユーザー情報が見つかりません: {}", username);
            throw new IllegalStateException("ユーザー情報が存在しません: " + username);
        }
        
        // ログイン失敗回数と最終ログイン日時を更新
        user.setLoginFailureCount(0);
        user.setLastLoginAt(LocalDateTime.now());
        usersService.save(user);
        
        // 認証成功ログ
        logger.debug("\n★★認証成功★★\n利用者： {}\n", username);
    }
}   
