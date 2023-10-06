package com.cdy.mol.backend.service.impl.predict.show.record.mark;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.MarkRecordMapper;
import com.cdy.mol.backend.pojo.MarkRecord;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.predict.show.record.mark.GetListMRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListMRServiceImpl implements GetListMRService {
    @Autowired
    private MarkRecordMapper markRecordMapper;
    @Override
    public JSONObject getList(Integer page) {
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

        IPage<MarkRecord> recordIPage = new Page<>(page, 5);
        QueryWrapper<MarkRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").eq("user_id", user.getId());
        List<MarkRecord> records = markRecordMapper.selectPage(recordIPage, queryWrapper).getRecords();

        resp.put("records", records);
        resp.put("records_count", markRecordMapper.selectCount(queryWrapper));
        resp.put("error_message", "success");
        return resp;
    }
}
