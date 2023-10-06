package com.cdy.mol.backend.service.impl.user.detail.notification;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.NotificationMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.Notification;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.detail.notification.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> update(Integer id) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Notification notification = notificationMapper.selectById(id);
        if (notification.getUserId() != user.getId()) {
            map.put("error_message", "用户信息错误");
            return map;
        }
        notification.setNotice(1);
        notificationMapper.updateById(notification);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        List<Notification> list = notificationMapper.selectList(queryWrapper);
        int count = 0;
        for (Notification no : list) {
            if (no.getNotice() == 0) count ++;
        }
        user.setNotification(count);
        userMapper.updateById(user);

        map.put("error_message", "success");
        return map;
    }
}
