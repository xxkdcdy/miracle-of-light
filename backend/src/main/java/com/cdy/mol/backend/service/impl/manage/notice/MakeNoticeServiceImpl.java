package com.cdy.mol.backend.service.impl.manage.notice;

import com.cdy.mol.backend.mapper.NotificationMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.Notification;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.notice.MakeNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MakeNoticeServiceImpl implements MakeNoticeService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public Map<String, String> notice(Integer userId, String content) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        User u = userMapper.selectById(userId);
        if (u == null) {
            map.put("error_message", "参数错误");
            return map;
        }
        u.setNotification(u.getNotification() + 1);
        Date now = new Date();
        Notification notification = new Notification(null, u.getId(), now, content, null);
        userMapper.updateById(u);
        notificationMapper.insert(notification);
        map.put("error_message", "success");
        return map;
    }
}
