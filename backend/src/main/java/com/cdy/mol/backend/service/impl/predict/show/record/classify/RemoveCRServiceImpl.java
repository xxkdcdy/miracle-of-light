package com.cdy.mol.backend.service.impl.predict.show.record.classify;

import com.cdy.mol.backend.mapper.ClassifyRecordMapper;
import com.cdy.mol.backend.pojo.ClassifyRecord;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.predict.show.record.classify.RemoveCRService;
import com.cdy.mol.backend.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveCRServiceImpl implements RemoveCRService {
    @Autowired
    private ClassifyRecordMapper classifyRecordMapper;
    @Override
    public Map<String, String> remove(Integer id) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        ClassifyRecord classifyRecord = classifyRecordMapper.selectById(id);
        if (classifyRecord.getUserId() != user.getId()) {
            map.put("error_message", "无删除权限！");
            return map;
        }
        if (classifyRecord.getClassify().contains("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/")) {
            String objectName = classifyRecord.getClassify().replace("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/", "");
            OSSUtil.deleteImage(objectName);
        }
        classifyRecordMapper.deleteById(classifyRecord);
        map.put("error_message", "success");
        return map;
    }
}
