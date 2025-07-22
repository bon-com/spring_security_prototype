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
    private String loginId;
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private Collection<? extends GrantedAuthority> authorities;
    /** ログイン失敗回数 */
    private int loginFailureCount;
    /** 最終ログイン日時 */
    private LocalDateTime lastLoginAt;

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
        LocalDateTime lastLoginAt
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
    }
}

