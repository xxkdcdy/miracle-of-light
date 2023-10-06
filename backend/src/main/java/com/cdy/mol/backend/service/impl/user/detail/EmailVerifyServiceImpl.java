package com.cdy.mol.backend.service.impl.user.detail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.EmailVerifyMapper;
import com.cdy.mol.backend.pojo.EmailVerify;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.detail.EmailVerifyService;
import com.cdy.mol.backend.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerifyServiceImpl implements EmailVerifyService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailVerifyMapper emailVerifyMapper;
    @Override
    public Map<String, String> sendEmail(String email) {
        Map<String, String> map = new HashMap<>();

        if (!email.contains("@")) {
            map.put("error_message", "请输入正确的邮箱地址");
            return map;
        }

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

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
