package com.cdy.mol.backend.controller.user.detail;

import com.cdy.mol.backend.service.user.detail.EmailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmailVerifyController {
    @Autowired
    private EmailVerifyService emailVerifyService;
    @PostMapping("/api/user/detail/sendmail/")
    public Map<String, String> sendEmail(@RequestParam Map<String, String> data) {
        String email = data.get("email");
        return emailVerifyService.sendEmail(email);
    }
}
