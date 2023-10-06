package com.cdy.mol.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.NotificationMapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.Notification;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能大于100");
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

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/icon_default.jpeg";
        User user = new User(null, username, encodedPassword, photo, 1, 1);
        userMapper.insert(user);

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("username", username);
        User registedUser = userMapper.selectOne(queryWrapper1);
        UserDetail userDetail = new UserDetail(null, registedUser.getId(), registedUser.getUsername(), null, null, null, null, null, null, 0, 0);
        userDetailMapper.insert(userDetail);

        Date now = new Date();
        Notification notification = new Notification(null, user.getId(), now, "欢迎注册，请及时绑定个人信息！", 0);
        notificationMapper.insert(notification);

        map.put("error_message", "success");
        return map;
    }
}

