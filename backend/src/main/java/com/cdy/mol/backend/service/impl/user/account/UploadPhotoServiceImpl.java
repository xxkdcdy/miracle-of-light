package com.cdy.mol.backend.service.impl.user.account;

import com.cdy.mol.backend.mapper.UserMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.user.account.UploadPhotoService;
import com.cdy.mol.backend.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadPhotoServiceImpl implements UploadPhotoService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> upload(String content) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUer = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUer.getUser();

        if (!(content.contains("data:image/png;base64") || content.contains("data:image/jpeg;base64,"))) {
            map.put("error_message", "请上传图片格式的文件！");
            return map;
        }

        String randomCode = UUID.randomUUID().toString();
        String objectName = "user/account/photo/" + randomCode + ".png";
        String result = OSSUtil.uploadImage(objectName, content);
        if (!"success".equals(result)) {
            map.put("error_message", result);
            return map;
        }

        // 更新用户头像地址信息
        if (user.getPhoto().contains("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/account/photo/")) {
            objectName = user.getPhoto().replace(
                    "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/account/photo/",
                    "user/account/photo/");
            OSSUtil.deleteImage(objectName);
        }
        user.setPhoto("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/account/photo/" + randomCode + ".png");
        userMapper.updateById(user);

        map.put("error_message", "success");
        return map;
    }
}
