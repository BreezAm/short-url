package com.breez.shorturl.controller;

import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.constants.Constants;
import com.breez.shorturl.controller.base.BaseController;
import com.breez.shorturl.entity.LoginUser;
import com.breez.shorturl.entity.request.LoginForm;
import com.breez.shorturl.service.IUserService;
import com.breez.shorturl.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Api(tags = {"权限验证接口"})
@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private IUserService userService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public R login(@RequestBody @Validated LoginForm loginForm) {
        logger.info("用户登录信息：{}", loginForm.toString());
        String token = userService.login(loginForm);
        HashMap<String, String> result = new HashMap<>();
        result.put(Constants.TOKEN, token);
        return R.ok(result);
    }

    @PostMapping("logout")
    @ApiOperation("退出登录")
    public R logout(HttpServletRequest request) {
        userService.exitLogin(request);
        return R.ok();
    }

    @GetMapping("getUserInfo")
    @ApiOperation("获取登录用户信息")
    public R getUserInfo(@RequestParam String token) {
        LoginUser currentUser = userSessionService.getCurrentUser();
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", currentUser.getUsername());
        result.put("avatar", Constants.DEFAULT_AVATAR);
        return R.ok(result);
    }
}
