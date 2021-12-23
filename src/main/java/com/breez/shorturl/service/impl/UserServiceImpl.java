package com.breez.shorturl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breez.shorturl.common.service.RedisCache;
import com.breez.shorturl.common.service.TokenService;
import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.constants.CacheConstant;
import com.breez.shorturl.constants.Constants;
import com.breez.shorturl.entity.Group;
import com.breez.shorturl.entity.LoginUser;
import com.breez.shorturl.entity.User;
import com.breez.shorturl.entity.dto.Mail;
import com.breez.shorturl.entity.enums.LevelEnum;
import com.breez.shorturl.entity.request.LoginForm;
import com.breez.shorturl.entity.request.PassForm;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.exceptions.ShortUrlException;
import com.breez.shorturl.mapper.GroupMapper;
import com.breez.shorturl.mapper.UserMapper;
import com.breez.shorturl.service.IUserService;
import com.breez.shorturl.util.MD5;
import com.breez.shorturl.util.MailUtil;
import com.breez.shorturl.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private GroupMapper groupMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public String login(LoginForm loginForm) {
        String password = loginForm.getPassword().trim();
        String username = loginForm.getUsername().trim();
        password = MD5.encrypt(password);

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, username);
        User u = baseMapper.selectOne(wrapper);
        if (Objects.isNull(u)) {
            logger.error(ResponseCode.NOT_EXIST.getMsg() + ": {}", username);
            throw new ShortUrlException(ResponseCode.NOT_EXIST.getCode(), ResponseCode.NOT_EXIST.getMsg());
        }
        if (!u.getPassword().equals(password)) {
            logger.error(ResponseCode.AUTH_FAILED.getMsg() + ": {}", username);
            throw new ShortUrlException(ResponseCode.AUTH_FAILED.getCode(), ResponseCode.AUTH_FAILED.getMsg());
        }
        if (u.getStatus().name().equals(Constants.DISABLE)) {
            logger.error(ResponseCode.USER_DISABLE.getMsg() + ": {}", username);
            throw new ShortUrlException(ResponseCode.USER_DISABLE.getCode(), ResponseCode.USER_DISABLE.getMsg());
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(u.getId());
        loginUser.setUsername(u.getUsername());
        return tokenService.createToken(loginUser);
    }

    @Override
    public void exitLogin(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Objects.nonNull(loginUser)) {
            String token = loginUser.getToken();
            tokenService.delLoginUser(token);
        }
    }

    @Override
    @Transactional
    public boolean addUser(User user, String code) {
        //校验验证码
        checkMailCode(user.getEmail(), code);
        //判断用户是否已经存在
        String username = user.getUsername();
        User exit = getUserByUsername(username);
        if (Objects.nonNull(exit)) {
            logger.error("用户已经存在： {}", username);
            throw new ShortUrlException(ResponseCode.EXIST.getCode(), ResponseCode.EXIST.getMsg());
        }
        //创建用户
        String password = user.getPassword();
        String apikey = UUID.fastUUID();
        user.setPassword(MD5.encrypt(password));
        user.setApikey(apikey);
        user.setIntegral(Constants.DEFAULT_INTEGRAL);
        user.setLevel(LevelEnum.ORDINARY);
        user.setAvatar(Constants.DEFAULT_AVATAR);
        user.setCreateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        baseMapper.insert(user);

        User u = getUserByUsername(username);

        //分配默认分组
        Group group = new Group();
        group.setUserId(u.getId());
        group.setName(Constants.DEFAULT_GROUP);
        group.setCreateTime(LocalDateTime.now());
        groupMapper.insert(group);
        return true;
    }

    /**
     * 校验验证码是否正确
     *
     * @param email 邮箱
     * @param code  验证码
     */
    private void checkMailCode(String email, String code) {
        String prefix = MailUtil.getPrefix(email);
        String exitCode = redisCache.getCacheObject(CacheConstant.REGISTER_MAIL_CODE + prefix);
        logger.info("发送的验证码：{}", exitCode);
        if (!code.trim().equals(exitCode)) {
            logger.error("验证码输入错误：{}", code);
            throw new ShortUrlException(ResponseCode.EMAIL_CHECK_ERROR.getCode(), ResponseCode.EMAIL_CHECK_ERROR.getMsg());
        }
    }

    @Override
    @Transactional
    public boolean updatePassword(PassForm passForm) {
        String newPass = passForm.getNewPass().trim();
        String oldPass = passForm.getOldPass().trim();

        LoginUser currentUser = userSessionService.getCurrentUser();

        User user = baseMapper.selectById(currentUser.getUserId());
        if (!user.getPassword().equals(MD5.encrypt(oldPass))) {
            logger.error("原密码输入错误");
            throw new ShortUrlException(ResponseCode.OLD_PASS_ERROR.getCode(), ResponseCode.OLD_PASS_ERROR.getMsg());
        }
        if (user.getPassword().equals(MD5.encrypt(newPass))) {
            logger.error("原密码和新密码相同");
            throw new ShortUrlException(ResponseCode.OLD_NEW_PASS_SAME.getCode(), ResponseCode.OLD_NEW_PASS_SAME.getMsg());
        }

        User save = new User();
        save.setPassword(MD5.encrypt(newPass));
        save.setId(currentUser.getUserId());
        baseMapper.updateById(save);
        return true;
    }

    private User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }

}
