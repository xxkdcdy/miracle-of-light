package com.cdy.mol.backend.service.impl.predict.show.record.mark;

import com.cdy.mol.backend.mapper.MarkRecordMapper;
import com.cdy.mol.backend.pojo.MarkRecord;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.predict.show.record.mark.RemoveMRService;
import com.cdy.mol.backend.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveMRServiceImpl implements RemoveMRService {
    @Autowired
    private MarkRecordMapper markRecordMapper;
    @Override
    public Map<String, String> remove(Integer id) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        MarkRecord markRecord = markRecordMapper.selectById(id);
        if (user.getId() != markRecord.getUserId()) {
            map.put("error_message", "无删除权限！");
            return map;
        }

        if (markRecord.getPhoto().contains("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/")) {
            String objectName = markRecord.getPhoto().replace("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/", "");
            OSSUtil.deleteImage(objectName);
        }

        if (markRecord.getMark().contains("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/")) {
            String objectName = markRecord.getMark().replace("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/", "");
            OSSUtil.deleteImage(objectName);
        }

        markRecordMapper.deleteById(markRecord);
        map.put("error_message", "success");
        return map;
    }
}
