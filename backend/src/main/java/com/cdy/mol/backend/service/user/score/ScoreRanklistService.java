package com.cdy.mol.backend.service.user.score;

import com.alibaba.fastjson.JSONObject;

public interface ScoreRanklistService {
    JSONObject getList(Integer page);
}
