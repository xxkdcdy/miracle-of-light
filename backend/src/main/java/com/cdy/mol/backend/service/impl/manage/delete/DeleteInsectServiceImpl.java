package com.cdy.mol.backend.service.impl.manage.delete;

import com.cdy.mol.backend.mapper.InsectMapper;
import com.cdy.mol.backend.pojo.User;
import com.cdy.mol.backend.service.impl.utils.UserDetailsImpl;
import com.cdy.mol.backend.service.manage.delete.DeleteInsectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteInsectServiceImpl implements DeleteInsectService {
    @Autowired
    private InsectMapper insectMapper;
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

        if (insectMapper.selectById(id) == null) {
            map.put("error_message", "参数错误");
            return map;
        }

        insectMapper.deleteById(id);
        map.put("error_message", "success");
        return map;
    }
}
