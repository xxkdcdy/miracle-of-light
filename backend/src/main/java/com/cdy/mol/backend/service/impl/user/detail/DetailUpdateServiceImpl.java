package com.cdy.mol.backend.service.impl.user.detail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.detail.DetailUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DetailUpdateServiceImpl implements DetailUpdateService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUer = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUer.getUser();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);

        String nickname = data.get("nickname");
        String address = data.get("address");
        String description = data.get("description");
        String organization = data.get("organization");

        if(nickname == null || nickname.length() == 0) {
            map.put("error_message", "昵称不能为空");
            return map;
        }

        userDetail.setNickname(nickname);
        userDetail.setAddress(address);
        userDetail.setDescription(description);
        userDetail.setOrganization(organization);
        userDetailMapper.updateById(userDetail);
        map.put("error_message", "success");
        return map;
    }
}
