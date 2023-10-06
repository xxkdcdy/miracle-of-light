package com.cdy.mol.backend.service.impl.manage.update;

import com.cdy.mol.backend.mapper.InsectMapper;
import com.cdy.mol.backend.pojo.Insect;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.update.InsectUpdateService;
import com.cdy.mol.backend.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InsectUpdateServiceImpl implements InsectUpdateService {
    @Autowired
    private InsectMapper insectMapper;
    @Override
    public Map<String, String> update(Integer id, String insectOrder, String family, String familyCode, String genus,
                                      String genusCode, String name, String nameCode, String nameLatin,
                                      String environment, String region, String suggest, String photo) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        Insect insect = insectMapper.selectById(id);
        if(insect == null) {
            map.put("error_message", "参数错误");
        }

        String url = insect.getUrl();
        if (photo != null && (photo.contains("data:image/png;base64") || photo.contains("data:image/jpeg;base64,"))) {
            String randomCode = UUID.randomUUID().toString();
            String objectName = "user/profile/insect/" + randomCode + ".png";
            String result = OSSUtil.uploadImage(objectName, photo);
            if (!"success".equals(result)) {
                map.put("error_message", result);
                return map;
            }
            if (url != null && url.contains("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/")) {
                url = url.replace("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/", "");
                OSSUtil.deleteImage(url);
            }
            url = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/" + objectName;
        }
        insect.setInsectOrder(insectOrder);
        insect.setFamily(family);
        insect.setFamilyCode(familyCode);
        insect.setGenus(genus);
        insect.setGenusCode(genusCode);
        insect.setName(name);
        insect.setNameCode(nameCode);
        insect.setNameLatin(nameLatin);
        insect.setEnvironment(environment);
        insect.setRegion(region);
        insect.setSuggest(suggest);
        insect.setUrl(url);
        insectMapper.updateById(insect);
        map.put("error_message", "success");
        return map;
    }
}
