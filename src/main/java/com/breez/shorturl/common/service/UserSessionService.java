package com.breez.shorturl.common.service;

import com.breez.shorturl.entity.LoginUser;
import org.springframework.stereotype.Service;
/**
 * 用户独占线程
 * @author BreezAm
 */
@Service
public class UserSessionService {
    private ThreadLocal<LoginUser> SESSION = new ThreadLocal<>();

    /**
     * 添加用户到SESSION
     * @param user
     */
    public void addSessionUser(LoginUser user) {
        SESSION.set(user);
    }

    public LoginUser getCurrentUser() {
        return SESSION.get();
    }

    public void removeUserSession() {
        SESSION.remove();
    }
}
