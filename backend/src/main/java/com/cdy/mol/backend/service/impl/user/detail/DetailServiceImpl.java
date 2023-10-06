package com.cdy.mol.backend.service.impl.user.detail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.detail.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DetailServiceImpl implements DetailService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public Map<String, String> getDetail() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);
        Map<String, String> map = new HashMap<>();
        String address = userDetail.getAddress();
        int len;
        // 地址信息隐藏
        if (address != null && address.length() > 3) {
            len = address.length();
            address = address.substring(0, 3);
            while (address.length() < len) address += "*";
        } else if (address != null && address.length() > 1) {
            len = address.length();
            address = address.substring(0, 1);
            while (address.length() < len) address += "*";
        }
        // 邮箱信息隐藏
        String email = userDetail.getEmail();
        if (email != null && email.length() > 0) {
            len = email.length();
            email = email.substring(email.indexOf('@'));
            while (email.length() < len) email = "*" + email;
        }
        // 姓名信息隐藏
        String name = userDetail.getName();
        if (name != null && name.length() > 0) {
            len = name.length();
            name = name.substring(0, 1);
            while (name.length() < len) name += "*";
        }
        // 身份证号信息隐藏
        String identification = userDetail.getIdentification();
        if (identification != null && identification.length() > 0) {
            identification = identification.substring(0, 3) + "***************";
        }
        map.put("error_message", "success");
        map.put("address", address);
        map.put("nickname", userDetail.getNickname());
        map.put("description", userDetail.getDescription());
        map.put("organization", userDetail.getOrganization());
        map.put("email", email);
        map.put("name", name);
        map.put("identification", identification);
        map.put("scoreTotal", userDetail.getScoreTotal().toString());
        map.put("scoreAvailable", userDetail.getScoreAvailable().toString());
        return map;
    }
}
