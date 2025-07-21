package com.example.prototype.security.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 利用者拡張エンティティ
 * Spring SecurityのUserを継承して拡張している
 */
public class ExtendedUser extends User {
    /** ログイン失敗回数 */
    private final int loginFailureCount;
    /** 最終ログイン日時 */
    private final LocalDateTime lastLoginAt;

    public ExtendedUser(
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
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.loginFailureCount = loginFailureCount;
        this.lastLoginAt = lastLoginAt;
    }

    public int getLoginFailureCount() {
        return loginFailureCount;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
    
}
