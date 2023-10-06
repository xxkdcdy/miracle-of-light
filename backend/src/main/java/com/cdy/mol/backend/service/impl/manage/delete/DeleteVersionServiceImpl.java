package com.cdy.mol.backend.service.impl.manage.delete;

import com.cdy.mol.backend.mapper.VersionMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.delete.DeleteVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteVersionServiceImpl implements DeleteVersionService {
    @Autowired
    private VersionMapper versionMapper;
    @Override
    public Map<String, String> delete(Integer id) {
        Map<String, String> map = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if (user.getFlag() != 2) {
            map.put("error_message", "权限错误");
            return map;
        }

        if (versionMapper.selectById(id) == null) {
            map.put("error_message", "参数错误");
            return map;
        }

        versionMapper.deleteById(id);
        map.put("error_message", "success");
        return map;
    }
}
