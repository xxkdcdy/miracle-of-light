package com.cdy.mol.backend.service.impl.user.score.exchange;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.ExchangeMapper;
import com.cdy.mol.backend.pojo.Exchange;
import com.cdy.mol.backend.service.user.score.exchange.GetExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExchangeServiceImpl implements GetExchangeService {
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Override
    public JSONObject getList(Integer page) {
        JSONObject resp = new JSONObject();
        IPage<Exchange> exchangeIPage = new Page<>(page, 3);
        List<Exchange> list = exchangeMapper.selectPage(exchangeIPage, null).getRecords();
        resp.put("notification", list);
        resp.put("records_count", exchangeMapper.selectCount(null));
        return resp;
    }
}
