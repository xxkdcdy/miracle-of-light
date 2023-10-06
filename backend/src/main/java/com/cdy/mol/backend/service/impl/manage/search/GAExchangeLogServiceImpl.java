package com.cdy.mol.backend.service.impl.manage.search;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.ExchangeLogMapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.pojo.ExchangeLog;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.search.GAExchangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GAExchangeLogServiceImpl implements GAExchangeLogService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private ExchangeLogMapper exchangeLogMapper;
    @Override
    public JSONObject getAll(Integer page) {
        JSONObject resp = new JSONObject();
        if (page < 1) {
            resp.put("records", null);
            resp.put("records_count", null);
            resp.put("error_message", "页码错误");
            return resp;
        }

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            resp.put("records", null);
            resp.put("records_count", null);
            resp.put("error_message", "权限错误");
            return resp;
        }

        IPage<ExchangeLog> exchangeLogIPage = new Page<>(page, 7);
        List<ExchangeLog> list = exchangeLogMapper.selectPage(exchangeLogIPage, null).getRecords();
        List<Map<String, JSONObject>> records = new ArrayList<>();

        for (ExchangeLog exchangeLog : list) {
            QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId());
            UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);
            Map<String, JSONObject> map = new HashMap<>();

            JSONObject log = new JSONObject(), name = new JSONObject(), address = new JSONObject();
            log.put("exchangeLog", exchangeLog);
            name.put("name", userDetail.getName());
            address.put("address", userDetail.getAddress());
            map.put("exchangeLog", log);
            map.put("name", name);
            map.put("address", address);

            records.add(map);
        }
        resp.put("records", records);
        resp.put("records_count", exchangeLogMapper.selectCount(null));
        resp.put("error_message", "success");
        return resp;
    }
}
