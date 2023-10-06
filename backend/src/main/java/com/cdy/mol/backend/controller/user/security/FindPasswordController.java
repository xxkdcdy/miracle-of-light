package com.cdy.mol.backend.controller.user.security;

import com.cdy.mol.backend.service.user.security.FindPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FindPasswordController {
    @Autowired
    private FindPasswordService findPasswordService;
    @PostMapping("/api/user/security/findpassword/")
    public Map<String, String> find_password(@RequestParam Map<String, String> data) {
        String code = data.get("code");
        String password = data.get("new_pwd");
        String confirmedPassword = data.get("repeat_pwd");
        return findPasswordService.find_password(code, password, confirmedPassword);
    }
}
