package com.cdy.mol.backend.service.impl.manage.search;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.ExchangeMapper;
import com.cdy.mol.backend.pojo.Exchange;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.search.GAExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GAExchangeServiceImpl implements GAExchangeService {
    @Autowired
    private ExchangeMapper exchangeMapper;

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

        IPage<Exchange> exchangeIPage = new Page<>(page, 7);
        List<Exchange> list = exchangeMapper.selectPage(exchangeIPage, null).getRecords();

        resp.put("records", list);
        resp.put("records_count", exchangeMapper.selectCount(null));
        resp.put("error_message", "success");
        return resp;
    }
}
