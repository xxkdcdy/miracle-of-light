package com.cdy.mol.backend.controller.user.security;

import com.cdy.mol.backend.service.user.security.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VerifyController {
    @Autowired
    private VerifyService verifyService;
    @PostMapping("/api/user/security/verify/email/")
    public Map<String, String> verify_email(@RequestParam Map<String, String> data) {
        String code = data.get("code");
        return verifyService.verify_email(code);
    }
    @PostMapping("/api/user/security/verify/id/")
    public Map<String, String> verify_id(@RequestParam Map<String, String> data) {
        String idCard = data.get("idCard");
        String name = data.get("name");
        return verifyService.verify_ID(idCard, name);
    }
}
