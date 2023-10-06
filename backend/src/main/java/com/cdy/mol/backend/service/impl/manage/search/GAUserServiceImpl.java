package com.cdy.mol.backend.service.impl.manage.search;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.search.GAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GAUserServiceImpl implements GAUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailMapper userDetailMapper;
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

        IPage<User> userIPage = new Page<>(page, 7);
        List<User> list = userMapper.selectPage(userIPage, null).getRecords();
        List<Map<String, String>> records = new ArrayList<>();

        // 这里根据不同需要放各种信息
        for (User u : list) {
            QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", u.getId());
            UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);
            Map<String, String> map = new HashMap<>();
            map.put("id", u.getId().toString());
            map.put("username", u.getUsername());
            map.put("flag", u.getFlag().toString());
            map.put("nickname", userDetail.getNickname());
            map.put("address", userDetail.getAddress());
            map.put("description", userDetail.getDescription());
            map.put("organization", userDetail.getOrganization());
            map.put("email", userDetail.getEmail());
            map.put("name", userDetail.getName());
            map.put("identification", userDetail.getIdentification());
            records.add(map);
        }

        resp.put("records", records);
        resp.put("records_count", userMapper.selectCount(null));
        resp.put("error_message", "success");
        return resp;
    }
}
