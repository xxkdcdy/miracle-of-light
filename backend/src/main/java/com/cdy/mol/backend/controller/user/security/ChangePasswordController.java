package com.cdy.mol.backend.controller.user.security;

import com.cdy.mol.backend.service.user.security.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChangePasswordController {
    @Autowired
    private ChangePasswordService changePasswordService;
    @PostMapping("/api/user/security/changepassword/")
    public Map<String, String> ChangePassword(@RequestParam Map<String, String> data) {
        String pwd = data.get("pwd");
        String newPwd = data.get("new_pwd");
        String repeatPwd = data.get("repeat_pwd");
        return changePasswordService.change_password(pwd, newPwd, repeatPwd);
    }
}
