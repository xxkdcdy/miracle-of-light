package com.cdy.mol.backend.controller.manage.update;

import com.cdy.mol.backend.service.manage.update.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserUpdateController {
    @Autowired
    private UserUpdateService userUpdateService;
    @PostMapping("/api/manage/update/user/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        Integer flag = Integer.parseInt(data.get("flag"));
        String nickname = data.get("nickname");
        String name = data.get("name");
        String address = data.get("address");
        String description = data.get("description");
        String email = data.get("email");
        String organization = data.get("organization");
        return userUpdateService.update(id, flag, nickname, name, address, description, email, organization);
    }
}
