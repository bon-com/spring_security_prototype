package com.example.prototype.biz.users.listener;

import javax.servlet.Filter;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class FilterDebugger implements SmartInitializingSingleton {

    @Autowired
    private FilterChainProxy filterChainProxy;

    public void afterSingletonsInstantiated() {
        for (SecurityFilterChain chain : filterChainProxy.getFilterChains()) {
            if (chain instanceof DefaultSecurityFilterChain) {
                DefaultSecurityFilterChain defaultChain = (DefaultSecurityFilterChain) chain;
                System.out.println("\n◆◆Filter chain for: " + defaultChain.getRequestMatcher() + "◆◆\n");
                for (Filter filter : defaultChain.getFilters()) {
                    System.out.println("  " + filter.getClass().getSimpleName());
                }
            }
        }
    }


}
