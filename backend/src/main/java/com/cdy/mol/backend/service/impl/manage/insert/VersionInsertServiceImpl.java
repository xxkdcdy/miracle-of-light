package com.cdy.mol.backend.service.impl.manage.insert;

import com.cdy.mol.backend.mapper.VersionMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.pojo.Version;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.insert.VersionInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VersionInsertServiceImpl implements VersionInsertService {
    @Autowired
    private VersionMapper versionMapper;
    @Override
    public Map<String, String> insert(String versionCode, String versionDescription, String versionUrl) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        Version version = new Version(null, versionCode, versionDescription, versionUrl, new Date());
        versionMapper.insert(version);
        map.put("error_message", "success");
        return map;
    }
}
