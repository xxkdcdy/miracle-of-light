package com.cdy.mol.backend.service.user.score.exchange;

import com.alibaba.fastjson.JSONObject;

public interface GetExchangeService {
    JSONObject getList(Integer page);
}
