package com.breez.shorturl.controller;

import com.breez.shorturl.common.service.MailService;
import com.breez.shorturl.common.service.RedisCache;
import com.breez.shorturl.constants.CacheConstant;
import com.breez.shorturl.entity.dto.Mail;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.util.MailUtil;
import com.breez.shorturl.util.R;
import com.breez.shorturl.util.UUID;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码接口")
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
    private final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private MailService mailService;

    /**
     * 获取邮箱验证码
     */
    @PostMapping("sendCode/{email}")
    public R getMailCode(
            @PathVariable
            @Email(message = "邮箱格式不正确")
            @NotEmpty(message = "邮箱不能为空") String email) {
        logger.info("向{}发送验证码",email);
        String authCode = UUID.uuid(6);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTo(email);
        mail.setSubject("短网址注册验证码");
        mail.setText("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <h2>尊敬的用户：您好</h2>\n" +
                "    <h3 style=\"text-align: center\">您本次的验证码为：<label style=\"color: red;border: 1px\">" + authCode + "</label>（有效期1分钟）</h3>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        boolean send_status = mailService.sendHtmlMail(mail);
         logger.info("验证码发送结果返回值：{}",send_status);
        if (send_status) {
            logger.info("验证码发送成功：{}",email);
            String prefix = MailUtil.getPrefix(email);
            redisCache.setCacheObject(CacheConstant.REGISTER_MAIL_CODE + prefix, authCode, 1, TimeUnit.MINUTES);
            return R.ok();
        } else {
            logger.error("验证码发送失败:{}",email);
            return R.error(ResponseCode.EMAIL_SEND_ERROR.getCode(), ResponseCode.EMAIL_SEND_ERROR.getMsg());
        }
    }
}
