package com.cdy.mol.backend.controller.user.detail;

import com.cdy.mol.backend.service.user.detail.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DetailController {
    @Autowired
    private DetailService detailService;
    @GetMapping("/api/user/detail/get/")
    public Map<String, String> getInfo() {
        return detailService.getDetail();
    }
}
