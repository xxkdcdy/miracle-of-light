package com.cdy.mol.backend.service.impl.manage.update;

import com.cdy.mol.backend.mapper.MarkRecordMapper;
import com.cdy.mol.backend.pojo.ExchangeLog;
import com.cdy.mol.backend.pojo.MarkRecord;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.update.MarkUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarkUpdateServiceImpl implements MarkUpdateService {
    @Autowired
    private MarkRecordMapper markRecordMapper;
    @Override
    public Map<String, String> update(Integer id, String state) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        MarkRecord markRecord = markRecordMapper.selectById(id);
        if (markRecord == null) {
            map.put("error_message", "参数错误");
            return map;
        }

        markRecord.setState(state);
        markRecordMapper.updateById(markRecord);
        map.put("error_message", "success");
        return map;
    }
}
