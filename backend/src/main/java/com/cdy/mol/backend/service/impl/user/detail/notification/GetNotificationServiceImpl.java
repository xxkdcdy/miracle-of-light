package com.cdy.mol.backend.service.impl.user.detail.notification;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.NotificationMapper;
import com.cdy.mol.backend.pojo.Notification;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.detail.notification.GetNotificationService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetNotificationServiceImpl implements GetNotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Override
    public JSONObject getList(Integer page) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        JSONObject resp = new JSONObject();

        IPage<Notification> notificationIPage = new Page<>(page, 3);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.orderByDesc("id");
        List<Notification> list = notificationMapper.selectPage(notificationIPage, queryWrapper).getRecords();
        resp.put("notification", list);
        resp.put("records_count", notificationMapper.selectCount(queryWrapper));
        return resp;
    }
}
