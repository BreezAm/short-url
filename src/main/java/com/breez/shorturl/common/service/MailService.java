package com.breez.shorturl.common.service;

import com.breez.shorturl.entity.dto.Mail;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.exceptions.ShortUrlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务
 *
 * @author BreezAm
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送带HTML格式的邮件
     *
     * @param mail 邮件实体
     */
    public boolean sendHtmlMail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom(mail.getFrom());
            message.setTo(mail.getTo());
            message.setText(mail.getText(), true);
            message.setSubject(mail.getSubject());
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            throw new ShortUrlException(ResponseCode.EMAIL_SEND_ERROR.getCode(), ResponseCode.EMAIL_SEND_ERROR.getMsg());
        }
    }
}
