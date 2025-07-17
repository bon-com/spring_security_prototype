package com.example.prototype.web.common.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.prototype.biz.utils.SessionCleaner;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @Autowired
    private SessionCleaner sessionCleaner;

    @ExceptionHandler({ DataAccessException.class })
    public ModelAndView handleDataAccessException(DataAccessException ex, HttpSession session) {
        // セッションクリア
        sessionCleaner.clearSession(session);

        // ログ出力
        logger.error("\n★★DBアクセス例外が発生★★：　{}", ex);

        // エラーページへ
        var mav = new ModelAndView("error");
        mav.addObject("message", "システムエラーが発生しました");
        return mav;
    }
}
