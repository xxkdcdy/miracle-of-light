package com.cdy.mol.backend.service.impl.user.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.EmailVerifyMapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.EmailVerify;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.user.security.SendEmailService;
import com.cdy.mol.backend.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private EmailVerifyMapper emailVerifyMapper;
    @Override
    public Map<String, String> sendEmail(String username, String email) {
        Map<String, String> map = new HashMap<>();

        if (!email.contains("@")) {
            map.put("error_message", "请输入正确的邮箱地址");
            return map;
        }

        if (username == null || username.length() == 0) {
            map.put("error_message", "请输入正确的用户名");
            return map;
        }

        // 获取用户信息，检查邮箱信息是否正确
        QueryWrapper queryWrapper0 = new QueryWrapper();
        queryWrapper0.eq("username", username);
        User user = userMapper.selectOne(queryWrapper0);

        if (user == null) {
            map.put("error_message", "用户名不存在");
            return map;
        }

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("user_id", user.getId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper1);

        if (userDetail.getEmail() == null) {
            map.put("error_message", "用户未绑定邮箱，请联系管理员");
            return map;
        }

        if (!userDetail.getEmail().equals(email)) {
            map.put("error_message", "绑定邮箱输入错误，请再次检查");
            return map;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        EmailVerify emailVerify = emailVerifyMapper.selectOne(queryWrapper);
        Date now = new Date();
        if (emailVerify == null) {
            emailVerify = new EmailVerify(null, user.getId(), null, null,
                    new Date(now.getTime() + 60000), null);
        } else if (now.getTime() - emailVerify.getExpiretime().getTime() < 59000) {
            map.put("error_message", "发送太频繁，请稍后再试");
            return map;
        }

        String code = EmailUtil.sendEmail(mailSender, from, email);
        if (code == null) {
            map.put("error_message", "发送错误，请重试");
        }

        emailVerify.setCode(code);
        emailVerify.setVerifyEmail(email);
        emailVerify.setExpiretime(new Date(now.getTime() + 60000));
        emailVerify.setVerifytime(new Date(now.getTime() + 300000));
        if (emailVerifyMapper.selectOne(queryWrapper) == null) emailVerifyMapper.insert(emailVerify);
        else emailVerifyMapper.updateById(emailVerify);

        map.put("error_message", "success");
        return map;
    }
}
