package com.cdy.mol.backend.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;

public class EmailUtil {
    public static String sendEmail(JavaMailSender mailSender, String from, String email) {
        try {
            //声明一个邮件发送请求
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //调用6位随机数随机数
            String code = randomCode();
            //邮件验证消息
            mailMessage.setSubject("邮件验证消息，5分钟内有效");
            //收到的验证码
            mailMessage.setText("收到的验证码：" + code);
            //发给谁
            mailMessage.setTo(email);
            //从哪发
            mailMessage.setFrom(from);
            //发送
            mailSender.send(mailMessage);
            return code;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 20位验证码
    public static String randomCode(){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i ++) {
            //从0-9中随机数
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
