package com.cdy.mol.backend.service.impl.user.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.EmailVerifyMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.EmailVerify;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.user.security.FindPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FindPasswordServiceImpl implements FindPasswordService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailVerifyMapper emailVerifyMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> find_password(String code, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        Date now = new Date();

        // 通过code找到验证信息
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        EmailVerify emailVerify = emailVerifyMapper.selectOne(queryWrapper);

        if (emailVerify == null) {
            map.put("error_message", "验证码不存在");
            return map;
        }

        User user = userMapper.selectById(emailVerify.getUserId());

        if (password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "密码长度不能大于100");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "两次输入的密码不一致");
            return map;
        }

        Date verifytime = emailVerify.getVerifytime();
        if (now.getTime() - verifytime.getTime() > 0) {
            map.put("error_message", "验证码过期！");
            return map;
        }

        emailVerify.setCode("null");
        emailVerifyMapper.updateById(emailVerify);
        user.setPassword(passwordEncoder.encode(password));
        userMapper.updateById(user);
        map.put("error_message", "success");
        return map;
    }
}
