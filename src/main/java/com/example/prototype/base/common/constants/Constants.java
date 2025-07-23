package com.example.prototype.base.common.constants;

/**
 * 定数クラス
 */
public class Constants {
    /** 認可エラーメッセージ */
    public static final String ERR_MSG_403 = "アクセス権限がありません";
    /** システムエラーメッセージ */
    public static final String ERR_MSG_500 = "システムエラーが発生しました";
    /** その他エラーメッセージ */
    public static final String ERR_MSG_DEFAULT = "予期せぬエラーが発生しました";
    /** 認証エラーメッセージ */
    public static final String ERR_MSG_AUTHENTICATION_BAD_CREDENTIALS = "ログインIDまたはパスワードが間違っています";
    /** 更新完了メッセージ */
    public static final String MSG_UPDATE_SUCCESS = "更新完了しました";
    /** 更新失敗メッセージ */
    public static final String MSG_UPDATE_ERR = "更新に失敗しました";
    /** アカウントロック種別：ロックあり */
    public static final String ACCOUNT_LOCKED = "ロックあり";
    /** アカウントロック種別：ロックなし */
    public static final String ACCOUNT_NOT_LOCKED = "ロックなし";
    /** 認証者用ウェルカムメッセージキー */
    public static final String WELCOME_MSG_KEY = "welcome.msg";
    /** 更新成功メッセージキー */
    public static final String UPDATE_SUCCESS_KEY = "update-success";
    /** ログイン失敗回数の閾値 */
    public static final int MAX_FAILURES = 3;
}
