package com.breez.shorturl.service;

import com.breez.shorturl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breez.shorturl.entity.request.LoginForm;
import com.breez.shorturl.entity.request.PassForm;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
public interface IUserService extends IService<User> {

    String login(LoginForm loginForm);


    void exitLogin(HttpServletRequest request);

    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean addUser(User user,String code);

    boolean updatePassword(PassForm passForm);

}
