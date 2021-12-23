package com.breez.shorturl.core.security.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.breez.shorturl.common.service.TokenService;
import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.entity.LoginUser;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 登录拦截器
 *
 * @author BreezAm
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserSessionService userSessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("登录token验证：{}",request.getRequestURI());
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Objects.nonNull(loginUser)){
            tokenService.verifyToken(loginUser);
            userSessionService.addSessionUser(loginUser);
            return true;
        }else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(R.error(ResponseCode.UNAUTHORIZED.getCode(),ResponseCode.UNAUTHORIZED.getMsg())));
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userSessionService.removeUserSession();
    }
}
