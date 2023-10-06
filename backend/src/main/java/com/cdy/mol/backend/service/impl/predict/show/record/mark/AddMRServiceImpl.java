package com.cdy.mol.backend.service.impl.predict.show.record.mark;

import com.cdy.mol.backend.mapper.MarkRecordMapper;
import com.cdy.mol.backend.pojo.MarkRecord;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.predict.show.record.mark.AddMRService;
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
public class AddMRServiceImpl implements AddMRService {
    @Autowired
    private MarkRecordMapper markRecordMapper;
    @Override
    public Map<String, String> add(String photo, String mark, String label) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String randomCode = UUID.randomUUID().toString();
        String objectName = "predict/show/record/mark/" + randomCode + ".jpg";
        String result = OSSUtil.uploadImage(objectName, photo);
        if (!"success".equals(result)) {
            map.put("error_message", result);
            return map;
        }
        String photoAddress = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/predict/show/record/mark/" + randomCode + ".jpg";

        randomCode = UUID.randomUUID().toString();
        objectName = "predict/show/record/mark/" + randomCode + ".jpg";
        result = OSSUtil.uploadImage(objectName, mark);
        if (!"success".equals(result)) {
            map.put("error_message", result);
            return map;
        }
        String markAddress = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/predict/show/record/mark/" + randomCode + ".jpg";

        Date now = new Date();
        MarkRecord markRecord = new MarkRecord(null, user.getId(), photoAddress, markAddress, label, now, null);
        markRecordMapper.insert(markRecord);

        map.put("error_message", "success");
        return map;
    }
}
