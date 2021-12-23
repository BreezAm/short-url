package com.breez.shorturl.controller;


import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.controller.base.BaseController;
import com.breez.shorturl.entity.LoginUser;
import com.breez.shorturl.entity.User;
import com.breez.shorturl.entity.request.PassForm;
import com.breez.shorturl.service.IUserService;
import com.breez.shorturl.util.R;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@RestController
@RequestMapping("/api/shorturl/user")
public class UserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private UserSessionService userSessionService;

    @GetMapping("getUserInfo")
    public R getUserInfo() {
        LoginUser currentUser = userSessionService.getCurrentUser();
        String userId = currentUser.getUserId();
        User user = userService.getById(userId);
        return R.ok(user);
    }

    @PutMapping("updatePass")
    public R updatePass(@RequestBody @Validated PassForm passForm) {
        return returnAjax(userService.updatePassword(passForm));
    }

    @ApiOperation("用户注册")
    @PostMapping("register/{code}")
    public R register(@Validated @RequestBody User user,@PathVariable String code) {
        logger.info("注册信息： {}", user);
        logger.info("用户输入的验证码：{}",code);
        return returnAjax(userService.addUser(user,code));
    }
}
