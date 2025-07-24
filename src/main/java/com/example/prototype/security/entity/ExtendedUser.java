package com.example.prototype.security.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * 利用者拡張エンティティ
 * Spring SecurityのUserを継承して拡張している
 */
@Data
public class ExtendedUser implements UserDetails {
    // ------------------------------
    // 既存フィールド ここから
    // ------------------------------
    /** ログインID */
    private String loginId;
    /** 利用者氏名 */
    private String username;
    /** パスワード */
    private String password;
    /** アカウント有効可否（true:有効/false:無効） */
    private boolean enabled;
    /** アカウント有効期限切れ可否（true:有効/false:期限切れ） */
    @SuppressWarnings("unused")
    private boolean accountNonExpired;
    /** パスワード有効期限切れ可否（true:有効/false:期限切れ） */
    @SuppressWarnings("unused")
    private boolean credentialsNonExpired;
    /** アカウントロック可否（true:ロックなし/false:ロックあり） */
    private boolean accountNonLocked;
    /** 権限リスト */
    private Collection<? extends GrantedAuthority> authorities;
    // ------------------------------
    // 既存フィールド ここまで
    // ------------------------------
    /** ログイン失敗回数 */
    private int loginFailureCount;
    /** 最終ログイン日時 */
    private LocalDateTime lastLoginAt;
    /** アカウント有効期限日時 */
    private LocalDateTime accountExpiryAt;
    /** パスワード有効期限日時 */
    private LocalDateTime passwordExpiryAt;

    public ExtendedUser(
        String loginId,
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities,
        int loginFailureCount,
        LocalDateTime lastLoginAt,
        LocalDateTime accountExpiryAt,
        LocalDateTime passwordExpiryAt
    ) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
        this.loginFailureCount = loginFailureCount;
        this.lastLoginAt = lastLoginAt;
        this.accountExpiryAt = accountExpiryAt;
        this.passwordExpiryAt = passwordExpiryAt;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        /*
         * このメソッドをオーバーライドすることで、falseが返却された場合
         * Spring Securityが自動でAccountExpiredExceptionをスローして認証時にアカウント有効期限切れエラーになる
         */
        return LocalDateTime.now().isBefore(accountExpiryAt);
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        /*
         * このメソッドをオーバーライドすることで、falseが返却された場合
         * Spring Securityが自動でCredentialsExpiredExceptionをスローして認証時にパスワード有効期限切れエラーになる
         */
        return LocalDateTime.now().isBefore(passwordExpiryAt);
    }
}

