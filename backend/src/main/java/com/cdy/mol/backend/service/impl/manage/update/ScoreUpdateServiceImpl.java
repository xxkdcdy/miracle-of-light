package com.cdy.mol.backend.service.impl.manage.update;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.UserDetailMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.UserDetail;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.update.ScoreUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreUpdateServiceImpl implements ScoreUpdateService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public Map<String, String> update(Integer userId, Integer score) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);

        if (userDetail == null || score < 0) {
            map.put("error_message", "参数错误");
            return map;
        }

        userDetail.setScoreAvailable(userDetail.getScoreAvailable() + score);
        userDetail.setScoreTotal(userDetail.getScoreTotal() + score);
        userDetailMapper.updateById(userDetail);
        map.put("error_message", "success");
        return map;
    }
}
