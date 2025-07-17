package com.example.prototype.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 利用者エンティティ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /** 利用者氏名 */
    private String userName;
    /** 利用者パスワード */
    private String password;
    /** 有効可否 */
    private boolean enabled;
}
