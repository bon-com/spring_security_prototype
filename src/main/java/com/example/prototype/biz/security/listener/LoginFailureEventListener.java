package com.example.prototype.biz.security.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureEventListener {
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(LoginFailureEventListener.class);

    /** 認証失敗後に呼ばれるイベントリスナー（※すべての失敗イベントを取得） */
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        var auth = event.getAuthentication();
        Exception ex = event.getException();

        String username = auth.getName();
        String clientInfo = auth.getDetails().toString();
        String failureType = ex.getClass().getSimpleName();
        String message = ex.getMessage();

        logger.debug("\n★★認証失敗★★:\n・ユーザー名: {}\n・失敗理由: {} ({})\n・詳細: {}\n", username, failureType, message, clientInfo);
    }

}
