package com.cdy.mol.backend.service.impl.manage.update;

import com.cdy.mol.backend.mapper.VersionMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.Version;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.update.VersionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VersionUpdateServiceImpl implements VersionUpdateService {
    @Autowired
    private VersionMapper versionMapper;
    @Override
    public Map<String, String> update(Integer id, String versionCode, String description, String url) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        Version version = versionMapper.selectById(id);
        if (version == null) {
            map.put("error_message", "参数错误");
            return map;
        }

        version.setVersionCode(versionCode);
        version.setVersionDescription(description);
        version.setVersionUrl(url);
        versionMapper.updateById(version);
        map.put("error_message", "success");
        return map;
    }
}
