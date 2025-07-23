package com.example.prototype.biz.security.listener;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.prototype.security.entity.ExtendedUser;

/**
 * 認可エラー時のエラーをハンドリングするクラス
 * ※認可エラー時はイベントが発行されない
 */
public class ExtendedAccessDeniedHandler implements AccessDeniedHandler {
    
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(ExtendedAccessDeniedHandler.class);
    
    /** 認可されていないロールでリクエストして403になっ多場合に、ハンドリング */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {
        
        // アクセス者のログ
        Authentication auth = (Authentication) request.getUserPrincipal();
        ExtendedUser authUser = (ExtendedUser) auth.getPrincipal();
        logger.debug("\n★★不正アクセス★★\n・ログインID: {}\n・アクセスパス: {}\n・理由: {}\n",
                authUser.getLoginId(), request.getRequestURI(), ex.getMessage());
        
        // エラー画面にリダイレクト
        response.sendRedirect(request.getContextPath() + "/system/error?code=403");

    }
}
