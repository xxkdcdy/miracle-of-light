package com.cdy.mol.backend.service.predict.data;

import com.alibaba.fastjson.JSONObject;

public interface GetListPDService {
    JSONObject getList(Integer page);
}
