package com.cdy.mol.backend.service.impl.user.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.EmailVerifyMapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.pojo.EmailVerify;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.security.VerifyService;
import com.cdy.mol.backend.utils.IDCardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private EmailVerifyMapper emailVerifyMapper;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public Map<String, String> verify_email(String code) {
        Map<String, String> map = new HashMap<>();

        Date now = new Date();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);
        EmailVerify emailVerify = emailVerifyMapper.selectOne(queryWrapper);

        if (emailVerify == null) {
            map.put("error_message", "验证码不存在！");
            return map;
        }

        Date verifytime = emailVerify.getVerifytime();
        if (now.getTime() - verifytime.getTime() > 0) {
            map.put("error_message", "验证码过期！");
            return map;
        }

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("user_id", emailVerify.getUserId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper1);
        userDetail.setEmail(emailVerify.getVerifyEmail());
        emailVerify.setCode("null");
        userDetailMapper.updateById(userDetail);
        emailVerifyMapper.updateById(emailVerify);

        map.put("error_message", "success");
        return map;
    }

    @Override
    public Map<String, String> verify_ID(String idCard, String name) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);
        if (IDCardUtil.checkIDCard(idCard, name)) {
            map.put("error_message", "success");
            userDetail.setIdentification(idCard);
            userDetail.setName(name);
            userDetailMapper.updateById(userDetail);
        } else {
            map.put("error_message", "认证身份失败，请重试");
        }
        return map;
    }
}
