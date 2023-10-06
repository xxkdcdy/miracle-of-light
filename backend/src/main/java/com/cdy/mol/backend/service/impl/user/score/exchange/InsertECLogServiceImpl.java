package com.cdy.mol.backend.service.impl.user.score.exchange;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.*;
import com.cdy.mol.backend.pojo.*;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.score.exchange.InsertECLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class InsertECLogServiceImpl implements InsertECLogService {
    @Autowired
    private ExchangeLogMapper exchangeLogMapper;
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> insert(Integer exchangeId) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Exchange exchange = exchangeMapper.selectById(exchangeId);
        if (exchange == null) {
            map.put("error_message", "参数错误");
            return map;
        }

        QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);

        // 检查积分是否足够
        if (userDetail.getScoreAvailable() < exchange.getScore()) {
            map.put("error_message", "积分不足！");
            return map;
        }

        // 记录结果
        Date now = new Date();
        ExchangeLog exchangeLog = new ExchangeLog(null, user.getId(), exchange.getId(), now, null);
        exchangeLogMapper.insert(exchangeLog);

        // 扣除对应的积分
        userDetail.setScoreAvailable(userDetail.getScoreAvailable() - exchange.getScore());
        userDetailMapper.updateById(userDetail);

        // 向用户发送一条消息
        String msg = "您已成功购买商品" + exchange.getTitle() + "，请注意查收！";
        Notification notification = new Notification(null, user.getId(), now, msg, null);
        notificationMapper.insert(notification);
        user.setNotification(user.getNotification() + 1);
        userMapper.updateById(user);

        map.put("error_message", "success");
        return map;
    }
}
