package com.example.prototype.biz.security.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessEventListener {
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessEventListener.class);
    
    /** 認証成功後に呼ばれるイベントリスナー */
    @EventListener
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String userName = event.getAuthentication().getName();
        logger.debug("\n★★認証成功★★\n利用者： {}\n", userName);
    }
}   
