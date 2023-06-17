package com.iware.bridge.online.service;

import java.util.List;
import java.util.Map;

public interface MailService {

    /** 发送普通邮件 **/
    public void sendSimpleMailMessage(String to, String subject, String content);

    /** 发送 HTML 邮件 **/
    public void sendMimeMessage(String to, String subject, String content);

    /** 批量发送 HTML 邮件 **/
    public void batchSendMessage(List<String> toList, String subject, String content);

    /** 发送带附件的邮件 **/
    public void sendMimeMessage(String to, String subject, String content, String filePath);

    /** 发送带静态文件的邮件 **/
    public void sendMimeMessage(String to, String subject, String content, Map<String, String> rscIdMap);
}
