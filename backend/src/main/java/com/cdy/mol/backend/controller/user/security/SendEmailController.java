package com.cdy.mol.backend.controller.user.security;

import com.cdy.mol.backend.service.user.security.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SendEmailController {
    @Autowired
    private SendEmailService sendEmailService;
    @PostMapping("/api/user/security/sendmail/")
    public Map<String, String> send_email(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String email = data.get("email");
        return sendEmailService.sendEmail(username, email);
    }
}
