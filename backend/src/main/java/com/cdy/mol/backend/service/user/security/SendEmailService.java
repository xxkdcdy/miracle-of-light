package com.cdy.mol.backend.service.user.security;

import java.util.Map;

public interface SendEmailService {
    Map<String, String> sendEmail(String username, String email);   // 未登录用户进行邮箱绑定的操作
}
