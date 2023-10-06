package com.cdy.mol.backend.service.impl.user.score;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.user.score.ScoreRanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreRanklistServiceImpl implements ScoreRanklistService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getList(Integer page) {
        IPage<UserDetail> userDetailIPage = new Page<>(page, 10);
        QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("score_total");
        List<UserDetail> userDetails = userDetailMapper.selectPage(userDetailIPage, queryWrapper).getRecords();
        List<Map<String, String>> resList = new ArrayList<>();
        JSONObject resp = new JSONObject();
        for (UserDetail userDetail : userDetails) {
            Integer userId = userDetail.getUserId();
            User user = userMapper.selectById(userId);
            Map<String, String> map = new HashMap<>();
            map.put("photo", user.getPhoto());
            map.put("nickname", userDetail.getNickname());
            map.put("username", user.getUsername());
            map.put("scoreTotal", userDetail.getScoreTotal().toString());
            resList.add(map);
        }
        resp.put("users", resList);
        resp.put("users_count", userDetailMapper.selectCount(null));
        return resp;
    }
}
