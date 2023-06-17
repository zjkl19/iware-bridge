package com.iware.bridge.online.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String SENDER;

    /**
     * 发送普通邮件
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMailMessage(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常!", e);
        }
    }

    /**
     * 发送 HTML 邮件
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendMimeMessage(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送MimeMessage时发生异常！", e);
        }
    }

    @Override
    public void batchSendMessage(List<String> toList, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            if (!CollectionUtils.isEmpty(toList)) {
                //true表示需要创建一个multipart message
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(SENDER);
                helper.setSubject(subject);
                helper.setText(content, true);
                helper.setTo(toList.stream().toArray(String[]::new));
                mailSender.send(message);
            }
        } catch (MessagingException e) {
            logger.error("发送MimeMessage时发生异常！", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
    public void sendMimeMessage(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);

            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送带附件的MimeMessge时发生异常！", e);
        }
    }

    /**
     * 发送带静态文件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param rscIdMap 需要替换的静态文件
     */
    @Override
    public void sendMimeMessage(String to, String subject, String content, Map<String, String> rscIdMap) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
                FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
                helper.addInline(entry.getKey(), file);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送带静态文件的MimeMessge时发生异常！", e);
        }
    }
}
