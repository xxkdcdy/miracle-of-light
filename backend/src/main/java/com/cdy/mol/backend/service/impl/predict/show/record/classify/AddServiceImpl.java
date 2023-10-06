package com.cdy.mol.backend.service.impl.predict.show.record.classify;

import com.cdy.mol.backend.mapper.ClassifyRecordMapper;
import com.cdy.mol.backend.pojo.ClassifyRecord;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.predict.show.record.classify.AddService;
import com.cdy.mol.backend.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AddServiceImpl implements AddService {
    @Autowired
    private ClassifyRecordMapper classifyRecordMapper;
    @Override
    public Map<String, String> add(String classify) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String randomCode = UUID.randomUUID().toString();
        String objectName = "predict/show/record/classify/" + randomCode + ".png";
        String result = OSSUtil.uploadImage(objectName, classify);
        if (!"success".equals(result)) {
            map.put("error_message", result);
            return map;
        }
        String imgAddress = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/predict/show/record/classify/" + randomCode + ".png";
        Date now = new Date();
        ClassifyRecord classifyRecord = new ClassifyRecord(null, user.getId(), imgAddress, now);
        classifyRecordMapper.insert(classifyRecord);
        map.put("error_message", "success");
        return map;
    }
}
