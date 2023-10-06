package com.cdy.mol.backend.service.user.detail;

import java.util.Map;

public interface EmailVerifyService {
    Map<String, String> sendEmail(String email);   // 登录用户进行邮箱绑定的操作
}
