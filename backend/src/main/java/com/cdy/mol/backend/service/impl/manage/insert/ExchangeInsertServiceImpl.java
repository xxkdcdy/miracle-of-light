package com.cdy.mol.backend.service.impl.manage.insert;

import com.cdy.mol.backend.mapper.ExchangeMapper;
import com.cdy.mol.backend.pojo.Exchange;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.insert.ExchangeInsertService;
import com.cdy.mol.backend.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ExchangeInsertServiceImpl implements ExchangeInsertService {
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Override
    public Map<String, String> insert(String title, String description, Integer score, String photo) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        if (photo == null || !(photo.contains("data:image/png;base64") || photo.contains("data:image/jpeg;base64,"))) {
            map.put("error_message", "请上传图片格式的文件！");
            return map;
        }

        String randomCode = UUID.randomUUID().toString();
        String objectName = "user/profile/exchange/" + randomCode + ".png";
        String result = OSSUtil.uploadImage(objectName, photo);
        if (!"success".equals(result)) {
            map.put("error_message", result);
            return map;
        }
        String url = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/" + objectName;
        Exchange exchange = new Exchange(null, title, description, score, url);
        exchangeMapper.insert(exchange);
        map.put("error_message", "success");
        return map;
    }
}
