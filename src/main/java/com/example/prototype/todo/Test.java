package com.example.prototype.todo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode("adminpass");
        System.out.println(hashed);
    }
}
