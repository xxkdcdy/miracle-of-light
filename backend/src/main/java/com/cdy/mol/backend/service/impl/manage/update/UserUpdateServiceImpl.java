package com.cdy.mol.backend.service.impl.manage.update;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.update.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserUpdateServiceImpl implements UserUpdateService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public Map<String, String> update(Integer id, Integer flag, String nickname, String name, String address, String description, String email, String organization) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        User u = userMapper.selectById(id);

        if (u == null) {
            map.put("error_message", "参数错误");
            return map;
        }

        QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", u.getId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);
        u.setFlag(flag);
        userDetail.setNickname(nickname);
        userDetail.setName(name);
        userDetail.setAddress(address);
        userDetail.setDescription(description);
        userDetail.setEmail(email);
        userDetail.setOrganization(organization);

        userMapper.updateById(u);
        userDetailMapper.updateById(userDetail);
        map.put("error_message", "success");
        return map;
    }
}
