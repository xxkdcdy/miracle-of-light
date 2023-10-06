package com.cdy.mol.backend.service.impl.manage.update;

import com.cdy.mol.backend.mapper.ExchangeLogMapper;
import com.cdy.mol.backend.pojo.ExchangeLog;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.update.ExchangeLogUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeLogUpdateServiceImpl implements ExchangeLogUpdateService {
    @Autowired
    private ExchangeLogMapper exchangeLogMapper;
    @Override
    public Map<String, String> update(Integer id) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        ExchangeLog exchangeLog = exchangeLogMapper.selectById(id);
        if (exchangeLog == null) {
            map.put("error_message", "参数错误");
            return map;
        }
        exchangeLog.setState("已发货");
        exchangeLogMapper.updateById(exchangeLog);
        map.put("error_message", "success");
        return map;
    }
}
